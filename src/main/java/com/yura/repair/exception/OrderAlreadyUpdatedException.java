package com.yura.repair.exception;

public class OrderAlreadyUpdatedException extends RuntimeException {
    public OrderAlreadyUpdatedException() {
    }

    public OrderAlreadyUpdatedException(String message) {
        super(message);
    }

    public OrderAlreadyUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
