package com.nooshhub.sample;

import com.nooshhub.retry.RetryContext;
import com.nooshhub.retry.RetryListener;

public class StatisticsRetryListener implements RetryListener {
    @Override
    public void onOpen(RetryContext retryContext) {
        System.out.println("=> Statistic for retry open");
    }

    @Override
    public void onSuccess(RetryContext retryContext) {
        System.out.println("= Statistic for retry success");
    }

    @Override
    public void onError(RetryContext retryContext) {
        System.out.println("= Statistic for retry error");
    }

    @Override
    public void onClose(RetryContext retryContext) {
        System.out.println("<= Statistic for retry close");
    }
}
