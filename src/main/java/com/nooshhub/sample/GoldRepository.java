package com.nooshhub.sample;

class GoldRepository {
    public Integer save(Integer amount) {
        if (amount == null) {
            throw new ConnectionException();
        } else {
            return amount + 1;
        }
    }
}