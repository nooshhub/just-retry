package com.nooshhub.retry;

class ConnectionException extends RuntimeException {

    public <T, E extends Throwable> T test() throws E {
        throw ConnectionException.<E>extracted();
    }

    private static <E extends Throwable> E extracted() throws E {
        throw (E) new Throwable();
    }
}