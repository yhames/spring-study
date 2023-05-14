package study.oopinspring.expert003;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest   // 여기서 @ExtendWith(SpringExtension.class) 포함
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/expert003/expert003.xml")
class CarTest {

    @Autowired
    Car car;

    @Test
    @DisplayName("스프링 설정파일(xml)에서 속성주입 - 한국")
    void springDITest1() {
        assertThat(car.getTireBrand()).isEqualTo("Car.getTireBrand() KoreanTire.getBrand()");
    }
}