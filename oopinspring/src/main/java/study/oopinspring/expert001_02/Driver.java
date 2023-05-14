package study.oopinspring.expert001_02;

/**
 * 생성자를 통한 의존관계 주입
 */
public class Driver {
    public static void main(String[] args) {
        Tire tire = new KoreanTire();
//        Tire tire = new AmericanTire();
        Car car = new Car(tire);
        System.out.println(car.getTireBrand());
    }
}
