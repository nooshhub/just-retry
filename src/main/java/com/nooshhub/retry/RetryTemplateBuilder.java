package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public class RetryTemplateBuilder {

    private int maxRetries = 3;
    private Class<? extends Throwable> retryOnException;
    private RetryListener retryListener;
    private long fixedDelay = 1000L;

    public RetryTemplateBuilder setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public RetryTemplateBuilder setRetryOnException(Class<? extends Throwable> retryOnException) {
        this.retryOnException = retryOnException;
        return this;
    }

    public RetryTemplateBuilder setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
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
        retryTemplate.setRetryListener(this.retryListener);
        return retryTemplate;
    }
}
