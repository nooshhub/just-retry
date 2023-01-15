package com.nooshhub.retry.delay;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class ExponentialDelayStrategy implements DelayStrategy {
    private long interval;
    private int multiplier;
    private Sleeper sleeper;

    public ExponentialDelayStrategy(long interval, int multiplier) {
        this.interval = interval;
        this.multiplier = multiplier;

        this.sleeper = new SimpleSleeper();
    }

    @Override
    public DelayContext start(RetryContext retryContext) {
        return new ExponentialDelayContext(this.interval);
    }

    @Override
    public void delay(DelayContext delayContext) {
        ExponentialDelayContext exponentialDelayContext = (ExponentialDelayContext) delayContext;
        long time = exponentialDelayContext.getInterval() * this.multiplier;
        // record the current interval for next interval
        exponentialDelayContext.setInterval(time);
        try {
            sleeper.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ExponentialDelayContext implements DelayContext {
        private long interval;

        public ExponentialDelayContext(long interval) {
            this.interval = interval;
        }

        public long getInterval() {
            return this.interval;
        }

        public void setInterval(long interval) {
            this.interval = interval;
        }
    }
}
