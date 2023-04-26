package com.kj.random_chatting.userChattingRoomList;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

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

        @Data
            public static class Builder {
                private String userId;
                private String roomId;
                private String roomName;
                private boolean isMovie;
                private boolean isDrive;

                public Builder() {}

                public inputDTO build() {
                    return new inputDTO(this);
                }
            }
    }

    @Data
    public static class outputDTO {
        private String userId;
        private String roomId;
        private String roomName;
        private boolean isMovie;
        private boolean isDrive;
    }
}
