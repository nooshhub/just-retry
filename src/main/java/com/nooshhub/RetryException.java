package com.nooshhub;

public class RetryException extends RuntimeException {
    public RetryException(Throwable cause) {
        super(cause);
    }
}
