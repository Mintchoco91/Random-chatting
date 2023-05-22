package com.kj.random_chatting.common;

public class Enum {
    public enum Gender {
        MAN("MAN"),
        WOMAN("WOMAN"),
        OTHER("OTHER");

        final private String name;

        private Gender(String name) { // enum에서 생성자 같은 역할
            this.name = name;
        }
        public String getName() { // 문자를 받아오는 함수
            return name;
        }
    }

    public enum PictureModalStatus {
        UPLOAD("UPLOAD"),
        DELETE("DELETE"),
        CLOSE("CLOSE");

        final private String name;

        private PictureModalStatus(String name) { // enum에서 생성자 같은 역할
            this.name = name;
        }
        public String getName() { // 문자를 받아오는 함수
            return name;
        }
    }
}
