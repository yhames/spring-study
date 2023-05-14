package study.oopinspring.aop002;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 스프링 AOP 적용
 */
@Aspect
public class MyAspect {

//    @Before("execution(public void study.oopinspring.aop002.Boy.run())")
    @Before("execution(* run())")
    public void before(JoinPoint joinPoint) {   // joinPoint -> Boy.run() 메서드를 의미
        System.out.println("비밀번호로 문을 열고 들어간다.");
    }
}
