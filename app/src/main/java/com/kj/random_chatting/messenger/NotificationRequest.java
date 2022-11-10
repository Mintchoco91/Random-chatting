package com.kj.random_chatting.messenger;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    @SerializedName("to")
    private String token;

    @SerializedName("data")
    private NotificationData data;
}
