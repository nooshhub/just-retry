package com.nooshhub.retry;

/**
 * @author Neal Shan
 * @since 2023/1/8
 */
public class RetryTemplate<T> {

    private int maxRetries = 3;
    private Class retryOnException;
    private RetryListener retryListener;
    private long fixedDelay = 1000L;

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Class getRetryOnException() {
        return this.retryOnException;
    }

    public void setRetryOnException(Class retryOnException) {
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

    public T execute(RetryCallback<T> retryCallback) {
        return doExecute(retryCallback, null);
    }

    public T execute(RetryCallback<T> retryCallback, RecoverCallback<T> recoverCallback) {
        return doExecute(retryCallback, recoverCallback);
    }

    private T doExecute(RetryCallback<T> retryCallback, RecoverCallback<T> recoverCallback) {

        // 重试上下文准备
        RetryContext retryContext = new RetryContext();

        // 重试开始
        onOpen(retryContext, retryListener);

        Throwable lastException = null;
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
                if (ex.getClass().isAssignableFrom(retryOnException)) {

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
                }

            } finally {
                // 重试结束
                onClose(retryContext, retryListener);
            }
        }

        // 重试次数耗尽
        if (lastException != null && retriedCount >= maxRetries) {
            if (recoverCallback != null) {
                return recoverCallback.recover(retryContext);
            } else {
                throw new RuntimeException(lastException);
            }
        }

        throw new RetryException(lastException);
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
