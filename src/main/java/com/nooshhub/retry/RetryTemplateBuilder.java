package com.nooshhub.retry;

import java.util.ArrayList;
import java.util.List;

import com.nooshhub.retry.delay.DelayStrategy;
import com.nooshhub.retry.delay.ExponentialDelayStrategy;
import com.nooshhub.retry.delay.FixedDelayStrategy;
import com.nooshhub.retry.delay.FixedRangeDelayStrategy;
import com.nooshhub.retry.delay.NoDelayStrategy;
import com.nooshhub.retry.delay.RandomDelayStrategy;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public class RetryTemplateBuilder {

    private int maxRetries = 3;
    private Class<? extends Throwable> retryOnException;
    private DelayStrategy delayStrategy = new NoDelayStrategy();
    private List<RetryListener> retryListeners = new ArrayList<>();

    public RetryTemplateBuilder setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public RetryTemplateBuilder setRetryOnException(Class<? extends Throwable> retryOnException) {
        this.retryOnException = retryOnException;
        return this;
    }

    public RetryTemplateBuilder registerRetryListener(RetryListener retryListener) {
        this.retryListeners.add(retryListener);
        return this;
    }

    public RetryTemplateBuilder setFixedDelay(long fixedDelay) {
        FixedDelayStrategy fixedDelayStrategy = new FixedDelayStrategy();
        fixedDelayStrategy.setTime(fixedDelay);
        this.delayStrategy = fixedDelayStrategy;
        return this;
    }

    public RetryTemplateBuilder setFixedRangeDelay(long t1, long t2, long t3) {
        long[] timeRange = new long[3];
        timeRange[0] = t1;
        timeRange[1] = t2;
        timeRange[2] = t3;
        FixedRangeDelayStrategy fixedRangeDelayStrategy = new FixedRangeDelayStrategy(timeRange);
        this.delayStrategy = fixedRangeDelayStrategy;
        return this;
    }

    public RetryTemplateBuilder setRandomDelay(int maxTime) {
        RandomDelayStrategy randomDelayStrategy = new RandomDelayStrategy(maxTime);
        this.delayStrategy = randomDelayStrategy;
        return this;
    }

    public RetryTemplateBuilder setExponentialDelay(int interval, int multiplier) {
        ExponentialDelayStrategy exponentialDelayStrategy = new ExponentialDelayStrategy(interval, multiplier);
        this.delayStrategy = exponentialDelayStrategy;
        return this;
    }

    public RetryTemplate build() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setMaxRetries(this.maxRetries);
        retryTemplate.setDelayStrategy(this.delayStrategy);
        retryTemplate.setRetryOnException(this.retryOnException);
        retryTemplate.setRetryListeners(this.retryListeners);
        return retryTemplate;
    }
}
