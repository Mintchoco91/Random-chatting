package com.kj.random_chatting.userChatting;

import lombok.Data;


public class UserChattingDTO {
    @Data
    public static class RoomInfo {
        private String roomId;
        private String roomName;
    }
}
