package com.nooshhub.retry;

public interface RecoverCallback<T> {
    T recover(RetryContext ctx);
}