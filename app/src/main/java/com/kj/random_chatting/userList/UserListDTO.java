package com.kj.random_chatting.userList;

import java.util.List;

import lombok.Data;

public class UserListDTO {
    @Data
    public static class outputDTO {
        private String id;
        private String userName;
        private String gender;
        private String age;
        private String phoneNumber;
        private List<String> fileNameList;
    }
}
