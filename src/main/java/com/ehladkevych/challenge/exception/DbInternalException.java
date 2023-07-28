package com.ehladkevych.challenge.exception;

public class DbInternalException extends RuntimeException {
    public DbInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
