package study.oopinspring.expert005;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 스프링 의존성(@Autowired) 제거, @Resource 사용
 */
@Getter
@Setter
public class Car {

    @Resource
    Tire tire;

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
