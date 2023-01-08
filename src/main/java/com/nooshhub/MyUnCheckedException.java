package com.nooshhub;

public class MyUnCheckedException extends RuntimeException{
    public MyUnCheckedException(Throwable throwable) {
        super(throwable);
    }
}
