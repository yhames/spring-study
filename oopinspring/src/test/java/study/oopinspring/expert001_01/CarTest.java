package study.oopinspring.expert001_01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CarTest {

    @Test
    @DisplayName("자동차 타이어 테스트")
    void carTireTest() {
        Car car = new Car();
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() KoreanTire.getBrand()");
    }
}