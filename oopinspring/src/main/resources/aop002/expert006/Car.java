package aop002.expert006;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    @Resource
    Tire tire;

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
