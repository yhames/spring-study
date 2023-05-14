package study.oopinspring.aop006;

import org.aspectj.lang.JoinPoint;

/**
 * After Advice 추가 - xml - 리펙토링
 */
public class MyAspect {

    public void before(JoinPoint joinPoint) {
        System.out.println("홍채인식으로 문을 열고 들어간다.");
    }

    public void lockDoor(JoinPoint joinPoint) {
        System.out.println("문을 잠그고 나간다.");
    }
}
