package hello.proxy.jdkdynamic.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Time Proxy 실행");
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);    // call 메서드 호출
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time Proxy 종료 result={}ms", resultTime);
        return result;
    }
}
