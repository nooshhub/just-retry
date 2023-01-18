package com.nooshhub.retry.annotation;

import com.nooshhub.retry.RetryTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryableAspect {

    @Pointcut(value = "execution(* *(..))")
    public void anyMethod() {
       // Pointcut for intercepting ANY method.
    }

    @Around("anyMethod() && @annotation(customAnnotation)")
    public Object invoke(final ProceedingJoinPoint pjp, final Retryable customAnnotation) throws Throwable {

        RetryTemplate retryTemplate = RetryTemplate.builder()
                .setMaxRetries(customAnnotation.maxRetries())
                .setRetryOnException(customAnnotation.retryOnException())
                .setFixedDelay(1000L)
//                .registerRetryListener(new StatisticsRetryListener())
//                .registerRetryListener(new LogRetryListener())
                .build();

        Object result = retryTemplate.execute(ctx -> {
            // goldRepository.save(amount);
            return pjp.proceed();
        });

        return result;
    }
}