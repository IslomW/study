package com.sharom.exception;

public enum Method {
    DATA_NOT_FOUND("data.not.found"),
    USER_NOT_FOUND("user.not.found"),
    USER_ALREADY_EXISTS("user.already.exists"),
    INVALID_PASSWORD("invalid.password");

    private final String code;


    Method(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
