package com.nooshhub.retry.delay;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public interface Sleeper {
    void sleep(long time) throws InterruptedException;
}
