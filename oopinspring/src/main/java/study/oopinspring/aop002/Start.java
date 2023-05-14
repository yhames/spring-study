package study.oopinspring.aop002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 스프링 AOP 적용
 */
public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop002/aop002.xml");

        Person boy = context.getBean("boy", Person.class);
        Person girl = context.getBean("girl", Person.class);

        boy.run();
        girl.run();
    }
}
