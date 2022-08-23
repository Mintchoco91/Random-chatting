package com.example.random_chatting.util;

import retrofit2.Call;
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

}
