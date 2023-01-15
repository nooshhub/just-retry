package com.nooshhub.retry.delay;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class NoDelayStrategy implements DelayStrategy {
    @Override
    public DelayContext start(RetryContext retryContext) {
        return null;
    }

    @Override
    public void delay(DelayContext delayContext) {
        // NOOP
        System.out.println("Sleep 0s.");
    }
}
