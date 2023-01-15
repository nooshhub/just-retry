package com.nooshhub.retry.delay;

import com.nooshhub.retry.RetryContext;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class FixedRangeDelayStrategy implements DelayStrategy {

    private long[] timeRange;
    private Sleeper sleeper;

    public FixedRangeDelayStrategy(long[] timeRange) {
        this.timeRange = timeRange;
        this.sleeper = new SimpleSleeper();
    }

    @Override
    public DelayContext start(RetryContext retryContext) {
        return new FixedRangeDelayContext(retryContext);

    }

    @Override
    public void delay(DelayContext delayContext) {
        FixedRangeDelayContext fixedDelayContext = (FixedRangeDelayContext) delayContext;
        int index = fixedDelayContext.getRetryContext().getRetryCount() - 1;
        long time = timeRange[index];
        try {
            sleeper.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class FixedRangeDelayContext implements DelayContext {
        private RetryContext retryContext;

        public FixedRangeDelayContext(RetryContext retryContext) {
            this.retryContext = retryContext;
        }

        public RetryContext getRetryContext() {
            return this.retryContext;
        }

        public void setRetryContext(RetryContext retryContext) {
            this.retryContext = retryContext;
        }
    }
}
