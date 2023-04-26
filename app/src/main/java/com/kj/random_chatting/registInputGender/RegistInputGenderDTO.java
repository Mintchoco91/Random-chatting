package com.kj.random_chatting.registInputGender;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class RegistInputGenderDTO {

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

}
