package study.oopinspring.aop005;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * After Advice 추가 - annotation
 */
@Aspect
public class MyAspect {

    @Before("execution(* run())")
    public void before(JoinPoint joinPoint) {
        System.out.println("홍채인식으로 문을 열고 들어간다.");
    }

    @After("execution(* run())")
    public void lockDoor(JoinPoint joinPoint) {
        System.out.println("문을 잠그고 나간다.");
    }
}
