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
    // 사진 업로드 개수
    public static final Integer	MAX_UPLOAD_PICTURE_COUNT = 5;

    public static final String EMPTY_IMAGE_PATH = "https://firebasestorage.googleapis.com/v0/b/random-chatting-b52bc.appspot.com/o/etc%2Fno_image.png?alt=media&token=b62b692a-6a40-49f7-a44f-3ff8c6cb41fa";

    public static final String SHARED_PREFERENCES_NAME = "userInfo";


}
