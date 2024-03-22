package com.springstudy.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {

    @Before("@annotation(com.springstudy.aop.exam.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("[TRACE] {}, args={}", joinPoint.getSignature(), args);
    }
}
