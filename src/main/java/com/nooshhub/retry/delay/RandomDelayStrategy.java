package com.nooshhub.retry.delay;

import java.util.Random;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class RandomDelayStrategy implements DelayStrategy {

    private Random random;
    private int maxTime;
    private Sleeper sleeper;

    public RandomDelayStrategy(int maxTime) {
        random = new Random();
        this.maxTime = maxTime;
        this.sleeper = new SimpleSleeper();
    }

    @Override
    public DelayContext start(RetryContext retryContext) {
        return null;
    }

    @Override
    public void delay(DelayContext delayContext) {
        int time = this.random.nextInt(this.maxTime);
        try {
            sleeper.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
