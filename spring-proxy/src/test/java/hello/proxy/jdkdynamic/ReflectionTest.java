package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    @DisplayName("no-reflection-test")
    void noReflection() {
        Hello target = new Hello();
        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();    // 호출하는 메서드만 다름
        log.info("result={}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();    // 호출하는 메서드만 다름
        log.info("result={}", result2);
        // 공통 로직2 종료
    }

    @Test
    @DisplayName("reflection-test")
    void reflection() throws Exception {
        // 클래스 메타정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");    // 내부클래스는 $표시로 구분

        Hello target = new Hello();

        // 메서드 메타정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        // 메서드 메타정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result1);

        /**
         * 기존의 직접 호출하던 부분(callA(), callB())가 Method 클래스로 추상화됨
         * 따라서 Method 클래스로 공통로직을 만들 수 있음.
         * 하지만 런타임에 동작하기 때문에 컴파일 시점에 오류를 잡을 수 없으므로
         * 지양하거나 주의하여 사용해야한다.
         */
    }

    @Test
    @DisplayName("reflection2-test")
    void reflection2() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");    // 내부클래스는 $표시로 구분

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);  // method 를 사용한 공통로직
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
