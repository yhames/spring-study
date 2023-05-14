package study.oopinspring.expert002;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.oopinspring.expert002.Car;
import study.oopinspring.expert002.KoreanTire;
import study.oopinspring.expert002.Tire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    @DisplayName("xml 생성 - 한국")
    void springDITest1() {
        Tire tire = new KoreanTire();
        Car car = new Car();
        car.setTire(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() KoreanTire.getBrand()");
    }

    @Test
    @DisplayName("xml 생성 - 미국")
    void springDITest2() {
        Tire tire = new AmericanTire();
        Car car = new Car();
        car.setTire(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() AmericanTire.getBrand()");
    }

}