package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public interface RetryCallback<T, E extends Throwable> {
    T doExecute(RetryContext ctx) throws E;
}
