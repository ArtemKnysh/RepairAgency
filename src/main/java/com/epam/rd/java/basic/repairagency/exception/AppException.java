package com.epam.rd.java.basic.repairagency.exception;

public class AppException extends Exception {

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException() {
    }
}
