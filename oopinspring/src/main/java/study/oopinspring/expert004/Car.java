package study.oopinspring.expert004;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 어노테이션 기반 의존관계 자동주입
 */
@Getter
@Setter
@AllArgsConstructor
public class Car {

    // 필드 주입은 누락 가능성이 있고 변동 가능성이 있어서 생성자 주입 방식으로 사용함
    private final Tire tire;

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
