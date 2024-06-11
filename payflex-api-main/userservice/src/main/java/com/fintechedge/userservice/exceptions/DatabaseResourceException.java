package com.fintechedge.userservice.exceptions;

public class DatabaseResourceException extends RuntimeException{
    public DatabaseResourceException(String message) {
        super(message);
    }
}
