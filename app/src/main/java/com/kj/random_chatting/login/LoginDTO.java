package com.kj.random_chatting.login;

import com.kj.random_chatting.common.Enum;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LoginDTO {
    @Data
    public static class input implements Serializable {
        private String email;
        private String password;
    }
    @Data
    public static class output{
        private String token;
        private String id;
        private String status;
        private String countryCode;
        private String phoneNumber;
        private String nickName;
        private String birthday;
        private String gender;
        private String email;
        private String password;
        //private List<String> uploadPhotoList;
    }
}
