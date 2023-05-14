package study.oopinspring.aop001;

/**
 * AOP 적용 전
 */
public class Start {
    public static void main(String[] args) {
        Boy boy = new Boy();
        Girl girl = new Girl();

        boy.run();
        girl.run();
    }
}
