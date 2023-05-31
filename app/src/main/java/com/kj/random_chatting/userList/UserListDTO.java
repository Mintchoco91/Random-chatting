package com.kj.random_chatting.userList;

import java.util.List;

import lombok.Data;

public class UserListDTO {
    @Data
    public static class outputDTO {
        private String id;
        private String nickName;
        private String birthday;
        private String gender;
        private String photoName;
        private String age;
    }
}
