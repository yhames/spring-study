package study.oopinspring.expert002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 스프링 DI 컨테이너 사용
 */
public class Driver {
    public static void main(String[] args) {
        // resources 경로에 없으면 파일 못찾음
        ApplicationContext context = new ClassPathXmlApplicationContext("expert002/expert002.xml");
        Car car = context.getBean("car", Car.class);
        Tire tire = context.getBean("tire", Tire.class);
        car.setTire(tire);
        System.out.println(car.getTireBrand());
    }
}
