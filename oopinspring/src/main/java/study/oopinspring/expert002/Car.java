package study.oopinspring.expert002;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    Tire tire;

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
