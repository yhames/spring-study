package study.oopinspring.aop001;

public class Girl {
    public void run() {
        System.out.println("열쇠로 문을 열고 들어간다.");
        try {
            System.out.println("설거지를 한다.");
        } catch (Exception e) {
            System.out.println("119에 신고를 한다.");
        } finally {
            System.out.println("소등하고 잔다.");
        }
        System.out.println("문을 잠그고 집을 나선다.");
        System.out.println();
    }
}
