package com.nooshhub.retry;

public interface RetryListener {
    void onOpen(RetryContext retryContext);

    void onSuccess(RetryContext retryContext);

    void onError(RetryContext retryContext);

    void onClose(RetryContext retryContext);
}
