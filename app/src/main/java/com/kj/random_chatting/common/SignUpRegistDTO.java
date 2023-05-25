package com.kj.random_chatting.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SignUpRegistDTO implements Serializable  {
    private String countryCode;
    private String phoneNumber;
    private String nickName;
    private String birthday;
    private Enum.Gender gender;
    private List<String> uploadFileList;
    private String email;
    private String password;


}
