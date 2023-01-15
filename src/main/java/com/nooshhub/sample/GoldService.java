package com.nooshhub.sample;

import com.nooshhub.retry.RetryTemplate;

public class GoldService {

    private final GoldRepository goldRepository = new GoldRepository();
    private final RetryTemplate retryTemplate = RetryTemplate.builder()
            .setRetryOnException(ConnectionException.class)
//            .setFixedDelay(1000L)
            .setFixedRangeDelay(1000L, 1200L, 1500L)
//            .setRandomDelay(1500)
//            .setExponentialDelay(500, 2)
            .registerRetryListener(new StatisticsRetryListener())
            .registerRetryListener(new LogRetryListener())
            .build();

//    public GoldService() {
//        retryTemplate.setRetryOnException(ConnectionException.class);
//        retryTemplate.setRetryListener(new StatisticsRetryListener());
//    }

    // 循环
    public void saveGold(Integer amount) throws Throwable {
        System.out.println("Run once!");

        // retry
//        retryTemplate.execute(ctx -> {
////            throw new Throwable();
//            throw new TestThrowable();
//        });

        // retry + recover
        retryTemplate.execute(ctx -> {
            return goldRepository.save(amount);
        }, ctx -> {
            System.out.println("* Recover from retry exhausted");
            return 0;
        });

    }

    public static void main(String[] args) throws Throwable {
        GoldService goldService = new GoldService();

        // test error
//        goldService.saveGold(THROW_ERROR);

        // test not retry on exception
//        goldService.saveGold(THROW_EXCEPTION);

        // success
//        goldService.saveGold(SUCCESS);

        // retry with exception
        goldService.saveGold(null);

    }
}





