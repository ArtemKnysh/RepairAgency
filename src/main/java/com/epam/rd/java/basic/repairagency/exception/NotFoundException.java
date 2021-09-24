package com.epam.rd.java.basic.repairagency.exception;

public class NotFoundException extends AppException {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
