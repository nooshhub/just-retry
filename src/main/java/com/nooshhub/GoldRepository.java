package com.nooshhub;

class GoldRepository {
    public void save(Integer amount) {
        throw new ConnectionException();
    }
}