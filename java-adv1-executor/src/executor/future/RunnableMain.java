package executor.future;

import java.util.Random;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableMain {

    /**
     * 12:45:36.541 [ Thread-1] Runnable 시작
     * 12:45:38.561 [ Thread-1] create value = 8
     * 12:45:38.562 [ Thread-1] Runnable 종료
     * 12:45:38.563 [     main] 최종 value = 8
     */
    public static void main(String[] args) throws InterruptedException {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task, "Thread-1");
        thread.start();
        thread.join();
        int value = task.value;
        log("최종 value = %d".formatted(value));
    }

    static class MyRunnable implements Runnable {

        int value;

        @Override
        public void run() {
            log("Runnable 시작");
            sleep(2000);
            value = new Random().nextInt(20);
            log("create value = %d".formatted(value));
            log("Runnable 종료");
        }
    }
}
