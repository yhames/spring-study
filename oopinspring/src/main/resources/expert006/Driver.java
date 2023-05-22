package aop002.expert006;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml 설정 파일 다양한 상황 가정하여 해보기
 */
public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("expert006/expert006.xml");
        Car car = context.getBean("car", Car.class);    // xml 파일에서 의존관계 주입
        System.out.println(car.getTireBrand());
    }
}
