package com.kj.random_chatting.util;

import com.kj.random_chatting.login.LoginRequest;
import com.kj.random_chatting.login.LoginResponse;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomCreateDTO;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userRegist.UserRegistDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retrofit_interface {
    @POST("searchUserList.php")
    Call<String> searchUserList();

    /* 나중에 필요할 때 주석 해제 할것(회원정보 수정)
    @POST("/modifyUserDetail.php/")
    Call<String> searchUserList();
    */

    /* 나중에 필요할 때 주석 해제 할것(회원정보 삭제)
    @POST("/deleteUserDetail.php/")
    Call<String> searchUserList();
    */

    @POST("userLogin.php")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @POST("searchChattingRoomList.php")
    Call<String> searchChattingRoomList(@Body UserChattingRoomListDTO.inputDTO inputParam);

    @POST("createUser.php")
    Call<String> createUser(@Body UserRegistDTO.inputDTO userRegistDTO);

    @POST("createChattingRoom.php")
    Call<String> createChattingRoom(@Body UserChattingRoomCreateDTO.inputDTO inputDTO);

    @POST("createChattingRoomDetail.php")
    Call<String> createChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);

    @POST("deleteChattingRoomDetail.php")
    Call<String> deleteChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);

    @POST("selectAndDeleteChattingRoomDetail.php")
    Call<String> selectAndDeleteChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);
}
