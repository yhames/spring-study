package study.oopinspring.expert001_03;

import lombok.Getter;
import lombok.Setter;

/**
 * 속성을 통한 의존관계 주입
 */
@Getter
@Setter
public class Car {

    Tire tire;

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
