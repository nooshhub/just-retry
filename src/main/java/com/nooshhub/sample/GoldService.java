package com.nooshhub.sample;

import com.nooshhub.retry.RetryListener;
import com.nooshhub.retry.RetryTemplate;

public class GoldService {

    private final GoldRepository goldRepository = new GoldRepository();
    private final RetryTemplate<Integer> retryTemplate = new RetryTemplate<>();

    public GoldService() {
        retryTemplate.setRetryOnException(ConnectionException.class);
        retryTemplate.setRetryListener(new StatisticsRetryListener());
    }

    // 循环
    public void saveGold(Integer amount) {
        System.out.println("Run once!");

        // retry
//        retryTemplate.execute(ctx -> {
//            return goldRepository.save(amount);
//        });

        // retry + recover
        retryTemplate.execute(ctx -> {
            return goldRepository.save(amount);
        }, ctx -> {
            System.out.println("* Recover from retry exhausted");
            return 0;
        });

    }

    public static void main(String[] args) {
        GoldService goldService = new GoldService();

        // success
        goldService.saveGold(1);

        // retry with exception
        goldService.saveGold(null);
    }
}





