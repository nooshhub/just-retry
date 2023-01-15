package com.nooshhub.retry.delay;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public interface DelayStrategy {
    DelayContext start(RetryContext retryContext);
    void delay(DelayContext delayContext);
}
