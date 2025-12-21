package com.sharom.exception;

import jakarta.ws.rs.core.Response;

public class BadRequestException extends RuntimeException {

    private final Response.Status status;


    public BadRequestException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }


    public static BadRequestException userNotFound(){
        return new BadRequestException(Response.Status.BAD_REQUEST, Method.USER_NOT_FOUND.getCode());
    }


    public static BadRequestException userAlreadyExists() {
        return new BadRequestException(Response.Status.BAD_REQUEST, Method.USER_ALREADY_EXISTS.getCode());
    }

    public static BadRequestException vocabularyNotFound() {
        return new BadRequestException(Response.Status.BAD_REQUEST, Method.DATA_NOT_FOUND.getCode());
    }


}
