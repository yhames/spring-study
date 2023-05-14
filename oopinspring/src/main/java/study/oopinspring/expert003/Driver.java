package study.oopinspring.expert003;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml 파일을 사용하여 의존관계 주입
 */
public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("expert003/expert003.xml");
        Car car = context.getBean("car", Car.class);    // xml 파일에서 의존관계 주입
        System.out.println(car.getTireBrand());
    }
}
