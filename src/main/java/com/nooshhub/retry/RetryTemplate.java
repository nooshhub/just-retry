package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public class RetryTemplate {

    private int maxRetries = 3;
    private Class<? extends Throwable> retryOnException;
    private RetryListener retryListener;
    private long fixedDelay = 1000L;

    public static RetryTemplateBuilder builder() {
        return new RetryTemplateBuilder();
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Class<? extends Throwable> getRetryOnException() {
        return this.retryOnException;
    }

    public void setRetryOnException(Class<? extends Throwable> retryOnException) {
        this.retryOnException = retryOnException;
    }

    public RetryListener getRetryListener() {
        return this.retryListener;
    }

    public void setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
    }

    public long getFixedDelay() {
        return this.fixedDelay;
    }

    public void setFixedDelay(long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E {
        return doExecute(retryCallback, null);
    }

    public <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback,
                                              RecoverCallback<T> recoverCallback) throws E {
        return doExecute(retryCallback, recoverCallback);
    }

    private <T, E extends Throwable> T doExecute(RetryCallback<T, E> retryCallback,
                                                 RecoverCallback<T> recoverCallback) throws E {

        // 重试上下文准备
        RetryContext retryContext = new RetryContext();

        Throwable lastException = null;
        try {
            // 重试开始
            onOpen(retryContext, retryListener);

            int retriedCount = 0;

            for (int i = 0; i < maxRetries; i++) {
                try {

                    T result = retryCallback.doExecute(retryContext);

                    // 重试成功
                    onSuccess(retryContext, retryListener);

                    return result;
                } catch (Throwable ex) {

                    lastException = ex;

                    // 重试异常类型检查
                    if (retryOnException.isAssignableFrom(ex.getClass())) {

                        // 记录重试次数
                        ++retriedCount;
                        System.out.println("Retry" + retriedCount);

                        // 延时
                        try {
                            Thread.sleep(fixedDelay);
                            System.out.println("- Sleep 1s");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // 重试错误
                        onError(retryContext, retryListener);

                        // 中断重试
                        throw RetryTemplate.<E>wrapIfNecessary(lastException);
                    }

                }
            }

            // 重试次数耗尽
            return handleRetryExhausted(recoverCallback, retryContext, lastException,
                    retriedCount);

        } catch (Throwable ex) {
            throw RetryTemplate.<E>wrapIfNecessary(lastException);
        } finally {
            // 重试结束
            onClose(retryContext, retryListener);
        }
    }

    private <T> T handleRetryExhausted(RecoverCallback<T> recoverCallback,
                                       RetryContext retryContext, Throwable lastException,
                                       int retriedCount) throws Throwable {
        if (lastException != null && retriedCount >= maxRetries) {
            if (recoverCallback != null) {
                return recoverCallback.recover(retryContext);
            } else {
                throw new RetryExhaustedException("Retry is exhausted.", lastException);
            }
        }
        throw wrapIfNecessary(lastException);
    }

    private static <E extends Throwable> E wrapIfNecessary(Throwable throwable)
            throws RetryException {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        } else if (throwable instanceof Exception) {
            @SuppressWarnings("unchecked")
            E rethrow = (E) throwable;
            return rethrow;
        } else {
            throw new RetryException(throwable);
        }
    }

    private void onOpen(RetryContext retryContext, RetryListener retryListener) {
        retryListener.onOpen(retryContext);
    }

    private void onSuccess(RetryContext retryContext, RetryListener retryListener) {
        retryListener.onSuccess(retryContext);
    }

    private void onError(RetryContext retryContext, RetryListener retryListener) {
        retryListener.onError(retryContext);
    }

    private void onClose(RetryContext retryContext, RetryListener retryListener) {
        retryListener.onClose(retryContext);
    }

}
