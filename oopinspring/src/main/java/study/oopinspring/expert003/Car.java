package study.oopinspring.expert003;

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
