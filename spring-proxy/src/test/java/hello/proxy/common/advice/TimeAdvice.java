package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("Time Proxy 실행");
        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();// target 로직 실행
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time Proxy 종료 result={}ms", resultTime);
        return result;
    }
}
