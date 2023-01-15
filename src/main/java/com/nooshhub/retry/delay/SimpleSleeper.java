package com.nooshhub.retry.delay;

/**
 * @author Neal Shan
 * @since 2023/1/15
 */
public class SimpleSleeper implements Sleeper {
    @Override
    public void sleep(long time) throws InterruptedException {
        try {
            System.out.println("- Sleep " + time/1000.0 + "s");
            Thread.sleep(time);
            System.out.println(" - wake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
