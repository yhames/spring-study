package study.oopinspring.expert001_02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CarTest {

    @Test
    @DisplayName("생성자을 통한 의존관계 주입 - 한국타이어")
    void korea() {
        Tire tire = new KoreanTire();
        Car car = new Car(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() KoreanTire.getBrand()");
    }

    @Test
    @DisplayName("생성자을 통한 의존관계 주입 - 미국타이어")
    void american() {
        Tire tire = new AmericanTire();
        Car car = new Car(tire);
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() AmericanTire.getBrand()");
    }
}