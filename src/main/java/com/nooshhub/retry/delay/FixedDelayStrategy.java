package com.nooshhub.retry.delay;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class FixedDelayStrategy implements DelayStrategy {
    private long time = 1000L;
    private Sleeper sleeper;

    public void setTime(long time) {
        this.time = time;
        this.sleeper = new SimpleSleeper();
    }

    @Override
    public DelayContext start(RetryContext retryContext) {
        return null;
    }

    @Override
    public void delay(DelayContext delayContext) {
        try {
            sleeper.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
