package com.kj.random_chatting.userLocation;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

public class UserLocationDTO {
    public static class InputDTO {
        @SerializedName("userId")
        public int userId;
        @SerializedName("userName")
        public String userName;
        @SerializedName("latitude")
        public double latitude;
        @SerializedName("longitude")
        public double longitude;

        public InputDTO(Builder builder) {
            userId = builder.userId;
            userName = builder.userName;
            latitude = builder.latitude;
            longitude = builder.longitude;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private int userId;
            private String userName;
            private double latitude;
            private double longitude;

            public Builder userId(int userId) {
                this.userId = userId;
                return this;
            }

            public Builder userName(String userName) {
                this.userName = userName;
                return this;
            }

            public Builder latitude(double latitude) {
                this.latitude = latitude;
                return this;
            }

            public Builder longitude(double longitude) {
                this.longitude = longitude;
                return this;
            }

            public InputDTO build() {
                return new InputDTO(this);
            }


        }

    }

    @IgnoreExtraProperties
    public static class OutputDTO {
        public String userName;
        public double latitude;
        public double longitude;

        public OutputDTO() {
            // Default constructor required for calls to DataSnapshot.getValue
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

}
