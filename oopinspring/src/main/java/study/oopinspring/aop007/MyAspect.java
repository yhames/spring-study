package study.oopinspring.aop007;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * After Advice 추가 - annotation - 리펙토링
 */
@Aspect
public class MyAspect {

    @Pointcut("execution(* run())")
    private void iampc() {

    }

    @Before("iampc()")
    public void before(JoinPoint joinPoint) {
        System.out.println("홍채인식으로 문을 열고 들어간다.");
    }

    @After("iampc()")
    public void lockDoor(JoinPoint joinPoint) {
        System.out.println("문을 잠그고 나간다.");
    }
}
