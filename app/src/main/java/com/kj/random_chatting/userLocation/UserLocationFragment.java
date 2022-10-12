package com.kj.random_chatting.userLocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.FragmentUserLocationBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.userList.FindUserInformationTaskRxJava;
import com.kj.random_chatting.userList.UserListService;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class UserLocationFragment extends Fragment implements MapView.CurrentLocationEventListener {
    private static final String TAG = "UserLocationFragment";
    private FragmentUserLocationBinding binding;
    private Context context;
    private FragmentActivity fragmentActivity;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private MapView mapView;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserLocationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void setListener() {
        // 리스너
    }

    @SuppressLint("MissingPermission")
    public void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();
        fragmentActivity = getActivity();

        // LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        // Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentLocation.getLatitude(), currentLocation.getLongitude()), true);

        mapView = new MapView(context);
        mapView.setCurrentLocationEventListener(this);

        // 먼저 GPS가 켜져있는지 확인한다.
        if (!checkLocationServiceStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRuntimePermission();
        }

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        ViewGroup mapViewContainer = binding.mapView;
        mapViewContainer.addView(mapView);

        // DB 작업등이 필요할 경우
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    public boolean checkLocationServiceStatus(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해 위치 서비스가 필요합니다.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent,GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.create().show();
    }

    public void checkRuntimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED){
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),REQUIRED_PERMISSIONS[0])){
                Toast.makeText(context,"이 앱을 실행하려면 위치 접근 권한이 필요합니다.",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(),REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(),REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
            }
        }
    }
}