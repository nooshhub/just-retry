package com.nooshhub;

interface RecoveryListener {
    void recover(RetryContext ctx);
}