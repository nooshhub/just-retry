package com.nooshhub.retry.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Neal Shan
 * @since 2023/1/17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAspectJAutoProxy
public @interface EnableRetry {
}
