package com.kj.random_chatting.UserChattingRoomList;

import com.google.gson.annotations.SerializedName;

public class UserChattingRoomListDTO {
    public static class inputDTO {
            @SerializedName("userId")
            private String userId;
            @SerializedName("roomId")
            private String roomId;
            @SerializedName("roomName")
            private String roomName;
            @SerializedName("isMovie")
            private boolean isMovie;
            @SerializedName("isDrive")
            private boolean isDrive;

            private inputDTO(Builder builder) {
                this.userId = builder.userId;
                this.roomId = builder.roomId;
                this.roomName = builder.roomName;
                this.isMovie = builder.isMovie;
                this.isDrive = builder.isDrive;
            }

            public static Builder builder() {
                return new Builder();
            }

            public static class Builder {
                private String userId;
                private String roomId;
                private String roomName;
                private boolean isMovie;
                private boolean isDrive;

                public Builder() {}

                public Builder setUserId(String userId) {
                    this.userId = userId;
                    return this;
                }

                public Builder setRoomId(String roomId) {
                    this.roomId = roomId;
                    return this;
                }

                public Builder setRoomName(String roomName) {
                    this.roomName = roomName;
                    return this;
                }

                public Builder setMovie(boolean isMovie) {
                    this.isMovie = isMovie;
                    return this;
                }

                public Builder setDrive(boolean isDrive) {
                    this.isDrive = isDrive;
                    return this;
                }

                public inputDTO build() {
                    return new inputDTO(this);
                }
            }
    }

    public static class outputDTO {
        private String userId;
        private String roomId;
        private String roomName;
        private boolean isMovie;
        private boolean isDrive;

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

        public boolean isMovie() {
            return isMovie;
        }

        public void setMovie(boolean movie) {
            isMovie = movie;
        }

        public boolean isDrive() {
            return isDrive;
        }

        public void setDrive(boolean drive) {
            isDrive = drive;
        }
    }
}
