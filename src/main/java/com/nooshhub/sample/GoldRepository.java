package com.nooshhub.sample;

class GoldRepository {
    public static final int SUCCESS = 1;
    public static final int THROW_ERROR = 2;
    public static final int THROW_EXCEPTION = 3;

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