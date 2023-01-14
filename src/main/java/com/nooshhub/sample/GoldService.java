package com.nooshhub.sample;

import com.nooshhub.retry.RetryListener;
import com.nooshhub.retry.RetryTemplate;

import static com.nooshhub.sample.GoldRepository.*;

public class GoldService {

    private final GoldRepository goldRepository = new GoldRepository();
    private final RetryTemplate retryTemplate = new RetryTemplate();

    public GoldService() {
        retryTemplate.setRetryOnException(ConnectionException.class);
        retryTemplate.setRetryListener(new StatisticsRetryListener());
    }

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





