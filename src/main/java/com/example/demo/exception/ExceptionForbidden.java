package com.example.demo.exception;

public class ExceptionForbidden extends RuntimeException {
    public ExceptionForbidden(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionForbidden(String message) {
        super(message);
    }
}
