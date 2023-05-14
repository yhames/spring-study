package study.oopinspring.expert004;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("expert004/expert004.xml");
        Car car = context.getBean("car", Car.class);    // xml 파일에서 의존관계 주입
        System.out.println(car.getTireBrand());
    }
}
