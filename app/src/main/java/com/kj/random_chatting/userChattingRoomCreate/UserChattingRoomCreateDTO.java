package com.kj.random_chatting.userChattingRoomCreate;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class UserChattingRoomCreateDTO {
    @Data
    public static class inputDTO {
        private String userId;
        private String roomId;
        private String roomName;
        private String isMovie;
        private String isDrive;
    }
}
