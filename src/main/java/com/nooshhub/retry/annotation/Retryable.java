package com.nooshhub.retry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Neal Shan
 * @since 2023/1/17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Retryable {

    int maxRetries() default 3;

    Class<? extends Throwable> retryOnException();
}
