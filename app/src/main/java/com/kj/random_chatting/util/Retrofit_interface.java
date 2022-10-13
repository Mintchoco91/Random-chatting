package com.kj.random_chatting.util;

import com.kj.random_chatting.login.LoginRequest;
import com.kj.random_chatting.login.LoginResponse;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomCreateDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userRegist.UserRegistDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retrofit_interface {
    @POST("/accountQuery.php/")
    @FormUrlEncoded
    Call<String> registDB(
            @Field("mode") String mode,
            @Field("userName") String userName,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("phoneNumber") String phoneNumber,
            @Field("fileName0") String fileName0,
            @Field("fileName1") String fileName1,
            @Field("fileName2") String fileName2,
            @Field("fileName3") String fileName3,
            @Field("fileName4") String fileName4,
            @Field("fileName5") String fileName5
    );

    @POST("/accountQuery.php/")
    @FormUrlEncoded
    Call<String> findUserInformation(
            @Field("mode") String mode
    );

    @POST("/userLogin.php")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @POST("/searchChattingRoomList.php")
    Call<String> searchChattingRoomList(@Body UserChattingRoomListDTO.inputDTO inputParam);

    @POST("/createUser.php")
    Call<String> createUser(@Body UserRegistDTO.inputDTO userRegistDTO);

    @POST("/createChattingRoom.php")
    Call<String> createChattingRoom(@Body UserChattingRoomCreateDTO.inputDTO inputDTO);
}
