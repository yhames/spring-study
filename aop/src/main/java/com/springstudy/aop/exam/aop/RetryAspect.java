package com.springstudy.aop.exam.aop;

import com.springstudy.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect()
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[RETRY] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exception = null;
        for(int retryCount = 1; retryCount <= maxRetry; retryCount++){
            try {
                log.info("[RETRY] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exception = e;
            }
        }
        throw exception;
    }
}
