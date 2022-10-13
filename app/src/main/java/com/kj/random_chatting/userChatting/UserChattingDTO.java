package com.kj.random_chatting.userChatting;

public class UserChattingDTO {
    public static class RoomInfo {
        private String roomId;
        private String roomName;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }
    }
}
