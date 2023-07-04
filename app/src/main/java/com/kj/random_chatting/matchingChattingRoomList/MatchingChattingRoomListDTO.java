package com.kj.random_chatting.matchingChattingRoomList;

import lombok.Data;

public class MatchingChattingRoomListDTO {
    @Data
    public static class outputDTO {
        private String id;
        private String nickName;
        private String birthday;
        private String gender;
        private String photoName;
        private String age;
    }

    @Data
    public static class searchUserInputDTO {
        private String userId;
    }

    @Data
    public static class matchingChatting {
        private String roomId;
        private String photoName;
        private String nickName;
        private String birthday;
    }
}
