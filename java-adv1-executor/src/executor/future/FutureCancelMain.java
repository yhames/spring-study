package executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {

    /**
     * 13:46:12.706 [     main] Future.state: RUNNING
     * 13:46:12.706 [pool-1-thread-1] 작업 중: 0
     * 13:46:13.720 [pool-1-thread-1] 작업 중: 1
     * 13:46:14.735 [pool-1-thread-1] 작업 중: 2
     * 13:46:15.713 [     main] future.cancel(true) 호출
     * 13:46:15.714 [pool-1-thread-1] 인터럽트 발생
     * 13:46:15.716 [     main] cancel(true) result: true
     * 13:46:15.716 [     main] Future는 이미 취소 되었습니다.
     */
    private static boolean mayInterruptIfRunning = true; // 변경


    /**
     * 13:45:35.911 [     main] Future.state: RUNNING
     * 13:45:35.911 [pool-1-thread-1] 작업 중: 0
     * 13:45:36.921 [pool-1-thread-1] 작업 중: 1
     * 13:45:37.922 [pool-1-thread-1] 작업 중: 2
     * 13:45:38.918 [     main] future.cancel(false) 호출
     * 13:45:38.922 [     main] cancel(false) result: true
     * 13:45:38.922 [     main] Future는 이미 취소 되었습니다.
     * 13:45:38.934 [pool-1-thread-1] 작업 중: 3
     * 13:45:39.942 [pool-1-thread-1] 작업 중: 4
     * 13:45:40.951 [pool-1-thread-1] 작업 중: 5
     * 13:45:41.958 [pool-1-thread-1] 작업 중: 6
     * 13:45:42.960 [pool-1-thread-1] 작업 중: 7
     * 13:45:43.974 [pool-1-thread-1] 작업 중: 8
     * 13:45:44.985 [pool-1-thread-1] 작업 중: 9
     */
//    private static boolean mayInterruptIfRunning = false; // 변경

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future.state: " + future.state());

        // 일정 시간 후 취소 시도
        sleep(3000);

        // cancel() 호출
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        // 결과 확인
        try {
            log("Future result: " + future.get());
        } catch (CancellationException e) {
            log("Future는 이미 취소 되었습니다.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        es.close();
    }

    static class MyTask implements Callable<String> {
        @Override
        public String call() {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중: " + i);
                    Thread.sleep(1000); // 1초 동안 sleep
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }

    }
}
