package com.yura.repair.exception;

public class InvalidPaginationException extends RuntimeException {

    public InvalidPaginationException() {
    }

    public InvalidPaginationException(String message) {
        super(message);
    }

    public InvalidPaginationException(String message, Throwable cause) {
        super(message, cause);
    }
}
