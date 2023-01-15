package com.nooshhub.retry;

public class RetryContext {
    private int retryCount;

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
