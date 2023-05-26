package com.kj.random_chatting.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SignUpRegistDTO  {
    @Data
    public static class input implements Serializable {
        private String countryCode;
        private String phoneNumber;
        private String nickName;
        private String birthday;
        private Enum.Gender gender;
        private String email;
        private String password;
        private List<String> uploadFileList;
    }

    @Data
    public static class output {
        private Integer resultCode;
        private String returnId;
    }
}
