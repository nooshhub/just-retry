package com.nooshhub.sample;

import com.nooshhub.retry.RetryContext;
import com.nooshhub.retry.RetryListener;

public class LogRetryListener implements RetryListener {
    @Override
    public void onOpen(RetryContext retryContext) {
        System.out.println("=> Log for retry open");
    }

    @Override
    public void onSuccess(RetryContext retryContext) {
        System.out.println("= Log for retry success");
    }

    @Override
    public void onError(RetryContext retryContext) {
        System.out.println("= Log for retry error");
    }

    @Override
    public void onClose(RetryContext retryContext) {
        System.out.println("<= Log for retry close");
    }
}
