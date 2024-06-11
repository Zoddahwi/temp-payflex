package com.fintechedge.userservice.exceptions;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(){}

    public ApiRequestException(String message) {
        super(message);
    }
}
