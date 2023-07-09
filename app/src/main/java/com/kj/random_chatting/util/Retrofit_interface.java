package com.kj.random_chatting.util;

import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.login.LoginDTO;
import com.kj.random_chatting.login.TokenDTO;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomCreateDTO;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userList.UserListDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Retrofit_interface {
    @POST("searchUserList.php")
    Call<String> searchUserList(@Body UserListDTO.searchUserInputDTO inputDTO);

    /* 나중에 필요할 때 주석 해제 할것(회원정보 수정)
    @POST("/modifyUserDetail.php/")
    Call<String> searchUserList();
    */

    /* 나중에 필요할 때 주석 해제 할것(회원정보 삭제)
    @POST("/deleteUserDetail.php/")
    Call<String> searchUserList();
    */

    @POST("userLogin.php")
    Call<String> getLoginResponse(@Body LoginDTO.input loginRequest);

    @POST("signIn.php")
    Call<LoginDTO.output> signIn(@Body LoginDTO.input loginRequest);

    @POST("renewToken.php")
    Call<TokenDTO.output> renewToken(@Body TokenDTO.input input);

    @POST("searchChattingRoomList.php")
    Call<String> searchChattingRoomList(@Body UserChattingRoomListDTO.inputDTO inputParam);

    @POST("signUpRegist.php")
    Call<String> signUpRegist(@Body SignUpRegistDTO.input intentData);

    @POST("signUpPhotoRegist.php")
    Call<String> signUpPhotoRegist(@Body SignUpRegistDTO.input intentData);

    @POST("createChattingRoom.php")
    Call<String> createChattingRoom(@Body UserChattingRoomCreateDTO.inputDTO inputDTO);

    @POST("createChattingRoomDetail.php")
    Call<String> createChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);

    @POST("deleteChattingRoomDetail.php")
    Call<String> deleteChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);

    @POST("selectAndDeleteChattingRoomDetail.php")
    Call<String> selectAndDeleteChattingRoomDetail(@Body UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO inputDTO);

    @POST("registMatching.php")
    Call<String> registMatching(@Body UserListDTO.matchingInputDTO input);
}
