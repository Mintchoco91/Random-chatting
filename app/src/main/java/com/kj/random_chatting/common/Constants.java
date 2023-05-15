package com.kj.random_chatting.common;

public final class Constants {
    public static final String	AWS_IP = "http://52.197.9.121";
    public static final String	AWS_PORT = "3000";
    public static final String	AWS_PHP_SERVER_IP_ADDRESS = AWS_IP + "/random-chatting-server/web/";
    public static final String	AWS_NODE_SERVER_IP_ADDRESS = AWS_IP + ":" + Constants.AWS_PORT;
    // 가입 시 인증 코드 자리수
    public static final Integer	AUTH_CODE_DIGIT = 4;
    // 가입 시 인증 코드 제한시간(second 단위)
    public static final Integer	AUTH_CODE_LIMIT_SECOND = 180;
}
