package com.sharom.exception;

public enum Method {
    DATA_NOT_FOUND("data.not.found"),
    USER_NOT_FOUND("user.not.found"),
    USER_ALREADY_EXISTS("user.already.exists"),
    EXAMPLE_NOT_FOUND("example.not.found"),
    INVALID_PASSWORD("invalid.password"),
    INVALID_POST_TYPE("invalid.post.type");

    private final String code;


    Method(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
