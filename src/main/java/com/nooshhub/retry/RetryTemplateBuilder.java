package com.nooshhub.retry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public class RetryTemplateBuilder {

    private int maxRetries = 3;
    private Class<? extends Throwable> retryOnException;
    private long fixedDelay = 1000L;
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
        this.fixedDelay = fixedDelay;
        return this;
    }

    public RetryTemplate build() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setMaxRetries(this.maxRetries);
        retryTemplate.setFixedDelay(this.fixedDelay);
        retryTemplate.setRetryOnException(this.retryOnException);
        retryTemplate.setRetryListeners(this.retryListeners);
        return retryTemplate;
    }
}
