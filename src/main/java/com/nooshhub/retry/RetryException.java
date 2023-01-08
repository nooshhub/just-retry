package com.nooshhub.retry;

public class RetryException extends RuntimeException {
    public RetryException(Throwable cause) {
        super(cause);
    }
}
