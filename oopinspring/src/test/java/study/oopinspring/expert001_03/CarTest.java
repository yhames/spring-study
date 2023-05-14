package study.oopinspring.expert001_03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.oopinspring.expert001_03.AmericanTire;
import study.oopinspring.expert001_03.Car;
import study.oopinspring.expert001_03.KoreanTire;
import study.oopinspring.expert001_03.Tire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    @DisplayName("속성을 통한 의존관계 주입 - 한국타이어")
    void korea() {
        Tire tire = new KoreanTire();
        Car car = new Car();
        car.setTire(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() KoreanTire.getBrand()");
    }

    @Test
    @DisplayName("속성을 통한 의존관계 주입 - 미국타이어")
    void american() {
        Tire tire = new AmericanTire();
        Car car = new Car();
        car.setTire(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() AmericanTire.getBrand()");
    }
}