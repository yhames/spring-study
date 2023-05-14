package study.oopinspring.expert001_02;

/**
 * 생성자를 통한 의존관계 주입
 */
public class Car {

    Tire tire;

    public Car(Tire tire) {
        this.tire = tire;   // 의존관계 없어짐
    }

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
