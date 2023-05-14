package study.oopinspring.aop002;

public class Girl implements Person {

    @Override
    public void run() {
        System.out.println("설거지를 한다.");
        System.out.println();
    }
}
