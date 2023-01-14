package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/11
 */
public class RetryExhaustedException extends RuntimeException {
    public RetryExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }
}
