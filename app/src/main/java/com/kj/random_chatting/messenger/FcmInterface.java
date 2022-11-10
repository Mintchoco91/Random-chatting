package com.kj.random_chatting.messenger;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FcmInterface {
    @Headers({"Authorization: key=AAAAbDYoiWM:APA91bEH_qNtbayaJZ8d2cj2XE5nk4V5QI9XltK2kddohCoQPBWoV6PkMd6Ph1MqX9m320NMJVZ9hd7Pofkaxe13reKCGktbh7mDmgvQzSxMXZFNt2f95iu_CYn-nqPOy4AWI8UPch6f",
            "Content-Type:application/json"})

    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body NotificationRequest notification);
}
