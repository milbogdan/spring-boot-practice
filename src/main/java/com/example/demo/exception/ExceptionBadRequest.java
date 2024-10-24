package com.example.demo.exception;

public class ExceptionBadRequest extends RuntimeException {
    public ExceptionBadRequest(String message) {
        super(message);
    }

    public ExceptionBadRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
