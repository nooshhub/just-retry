package com.nooshhub.retry;

import com.nooshhub.retry.annotation.Retryable;

import org.springframework.stereotype.Repository;

@Repository
class GoldRepository {
    public static final int SUCCESS = 1;
    public static final int THROW_ERROR = 2;
    public static final int THROW_EXCEPTION = 3;

    @Retryable(maxRetries = 3, retryOnException = ConnectionException.class)
    public Integer save(Integer amount) {
        if (amount == null) {
            throw new ConnectionException();
        } else if (amount == THROW_ERROR) {
            throw new TestError();
        } else if (amount == THROW_EXCEPTION){
            throw new TestRuntimeException();
        } else if (amount == SUCCESS){
            return amount + 1;
        } else {
            return SUCCESS;
        }
    }
}