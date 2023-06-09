package com.kj.random_chatting.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Enum {
    @Getter
    @AllArgsConstructor
    public enum Gender {
        MAN("MAN"),
        WOMAN("WOMAN"),
        OTHER("OTHER");

        final private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionStatus {
        DISLIKE("DISLIKE"),
        LIKE("LIKE"),
        SUPERLIKE("SUPERLIKE");

        final private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum PictureModalStatus {
        UPLOAD("UPLOAD"),
        DELETE("DELETE"),
        CLOSE("CLOSE");

        final private String name;
    }
}
