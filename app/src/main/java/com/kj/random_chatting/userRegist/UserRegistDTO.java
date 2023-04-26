package com.kj.random_chatting.userRegist;

import lombok.Data;

public class UserRegistDTO {
    @Data
    public static class inputDTO {
        private String email;
        private String password;
        private String userName;
        private String gender;
        private String age;
        private String phoneNumber;
        private String fileName0;
        private String fileName1;
        private String fileName2;
        private String fileName3;
        private String fileName4;
        private String fileName5;
    }
}
