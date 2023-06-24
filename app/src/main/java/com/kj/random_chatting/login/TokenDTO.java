package com.kj.random_chatting.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TokenDTO {
    @Data
    @AllArgsConstructor
    public static class input {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class output {
        private String accessToken;
        private String refreshToken;
        private boolean successful;
    }
}
