package com.kj.random_chatting.userChattingRoomCreate;


import lombok.Data;

public class UserChattingRoomDetailDTO {
    @Data
    public static class ChattingRoomDetailinputDTO {
        private String userId;
        private String roomId;
    }
}
