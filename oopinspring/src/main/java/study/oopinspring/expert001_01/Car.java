package study.oopinspring.expert001_01;

/**
 * 의존관계 O
 */
public class Car {

    Tire tire;

    public Car() {
        tire = new KoreanTire();    // 자동차(Car)가 타이어(Tire)에 의존
//        this.tire = new AmericanTire();
    }

    public String getTireBrand() {
        return "Car.getTireBrand() " + tire.getBrand();
    }
}
