package study.oopinspring.expert001_03;

/**
 * 속성(setter)을 통한 의존관계 주입
 */
public class Driver {
    public static void main(String[] args) {
        Tire tire = new KoreanTire();
        Car car = new Car();
        car.setTire(tire);

        System.out.println(car.getTireBrand());
    }

}
