package study.oopinspring.aop002;

public class Boy implements Person {

    @Override
    public void run() {
        System.out.println("빨래를 한다.");
        System.out.println();
    }
}
