package com.kj.random_chatting.userChattingRoomCreate;

import com.google.gson.annotations.SerializedName;

public class UserChattingRoomCreateDTO {
    public static class inputDTO {
        private String userId;
        private String roomId;
        private String roomName;
        private String isMovie;
        private String isDrive;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

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

        public String getIsMovie() {
            return isMovie;
        }

        public void setIsMovie(String isMovie) {
            this.isMovie = isMovie;
        }

        public String getIsDrive() {
            return isDrive;
        }

        public void setIsDrive(String isDrive) {
            this.isDrive = isDrive;
        }
    }
}
