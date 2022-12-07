package com.kj.random_chatting.userLocation;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserLocationDTO {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputDTO {
        @SerializedName("userId")
        public int userId;
        @SerializedName("userName")
        public String userName;
        @SerializedName("latitude")
        public double latitude;
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("fcmToken")
        public String fcmToken;
    }

    @IgnoreExtraProperties
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OutputDTO {
        private String userName;
        private double latitude;
        private double longitude;
        private String fcmToken;
    }

}
