package study.oopinspring.aop003;

import org.aspectj.lang.JoinPoint;

/**
 * 스프링 종속성 제거, xml 파일 설정
 */
public class MyAspect {

    public void before(JoinPoint joinPoint) {
        System.out.println("홍채인식으로 문을 열고 들어간다.");
    }
}
