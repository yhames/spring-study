package study.oopinspring.expert005;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("expert005/expert005.xml");
        Car car = context.getBean("car", Car.class);    // xml 파일에서 의존관계 주입
        System.out.println(car.getTireBrand());
    }
}
