package com.nooshhub.retry;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.nooshhub.retry.GoldRepository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JustTryTest {
    @Autowired
    private GoldService goldService;

    @Test
    public void retryAndExhausted() {
        assertThrows(RetryExhaustedException.class, () -> {
            goldService.saveGold(null);
        });
    }

    @Test
    public void retrySucceed() {
        Integer actual = goldService.saveGold(SUCCESS);
        assertEquals(SUCCESS + 1, actual, "amount should be increased 1");
    }

    @Test
    public void notRetryOnException() {
        assertThrows(TestRuntimeException.class, () -> {
            goldService.saveGold(THROW_EXCEPTION);
        });
    }

    @Test
    public void retryError() {
        assertThrows(TestError.class, () -> {
            goldService.saveGold(THROW_ERROR);
        });
    }
}
