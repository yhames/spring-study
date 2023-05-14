package study.oopinspring.aop003;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 스프링 종속성 제거, xml 파일 설정
 */
public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop003/aop003.xml");

        Person boy = context.getBean("boy", Person.class);
        Person girl = context.getBean("girl", Person.class);

        boy.run();
        girl.run();
    }
}
