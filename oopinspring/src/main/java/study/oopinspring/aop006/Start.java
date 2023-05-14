package study.oopinspring.aop006;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * After Advice 추가 - xml - 리펙토링
 */
public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop006/aop006.xml");

        Person boy = context.getBean("boy", Person.class);
        Person girl = context.getBean("girl", Person.class);

        boy.run();
        girl.run();
    }
}
