package hello.proxy.cglib.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * JDK 동적 프록시는 "인터페이스"를 "구현(implements)"하여 프록시를 생성하고
 * CGLIB 는 "구체 클래스"를 "상속(extends)"하여 프록시를 생성한다.
 */
@Slf4j
@RequiredArgsConstructor
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("Time Proxy 실행");
        long startTime = System.currentTimeMillis();
        Object result = methodProxy.invoke(target, args);   // 성능상 method 보다 methodProxy 권장
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time Proxy 종료 result={}ms", resultTime);
        return result;
    }
}
