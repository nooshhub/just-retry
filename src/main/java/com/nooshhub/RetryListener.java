package com.nooshhub;

public interface RetryListener {
    void onOpen(RetryContext retryContext);

    void onSuccess(RetryContext retryContext);

    void onError(RetryContext retryContext);

    void onClose(RetryContext retryContext);
}
