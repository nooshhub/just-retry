package com.nooshhub;

public class IteratedGoldService {

    private final GoldRepository goldRepository = new GoldRepository();

    // 循环
    public void saveGold(Integer amount) {
        System.out.println("Run once!");

        // 重试参数准备
        int maxRetries = 3;
        Class retryOnException = ConnectionException.class;
        RecoveryListener recoveryListener = ctx -> System.out.println("Try recoveryListener!");
        RetryListener retryListener = new StatisticsRetryListener();
        long fixedDelay = 1000L;

        // 重试上下文准备
        RetryContext retryContext = new RetryContext();

        // 重试开始
        onOpen(retryContext, retryListener);

        Throwable lastException = null;
        int retriedCount = 0;

        for (int i = 0; i < maxRetries; i++) {
            try {

                goldRepository.save(amount);

                // 重试成功
                onSuccess(retryContext, retryListener);

                break;
            } catch (Throwable ex) {

                lastException = ex;

                // 重试异常类型检查
                if (ex.getClass().isAssignableFrom(retryOnException)) {

                    // 记录重试次数
                    ++retriedCount;
                    System.out.println("retry" + retriedCount);

                    // 延时
                    try {
                        Thread.sleep(fixedDelay);
                        System.out.println("sleep 1s");
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
        if (lastException != null || retriedCount >= maxRetries) {
            if (recoveryListener != null) {
                recoveryListener.recover(retryContext);
            } else {
                throw new RuntimeException(lastException);
            }
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


    public static void main(String[] args) {
        IteratedGoldService goldService = new IteratedGoldService();
        goldService.saveGold(1);
    }
}





