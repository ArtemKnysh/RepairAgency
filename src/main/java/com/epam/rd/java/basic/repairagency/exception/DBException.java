package com.epam.rd.java.basic.repairagency.exception;

public class DBException extends AppException {

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException() {
    }
}
