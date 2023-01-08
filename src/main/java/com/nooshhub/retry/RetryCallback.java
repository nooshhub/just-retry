package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public interface RetryCallback<T> {
    T doExecute(RetryContext ctx);
}
