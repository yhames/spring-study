package executor.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV2 {

    /**
     * 12:46:58.579 [     main] submit() 호출
     * 12:46:58.585 [     main] future 반환, future=java.util.concurrent.FutureTask@6576fe71[Not completed, task = executor.future.CallableMainV2$MyCallable@7eda2dbb]
     * 12:46:58.585 [pool-1-thread-1] Callable 시작
     * 12:46:58.586 [     main] future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING
     * 12:47:00.599 [pool-1-thread-1] create value = 10
     * 12:47:00.599 [pool-1-thread-1] Callable 종료
     * 12:47:00.599 [     main] future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE
     * 12:47:00.600 [     main] 최종 result = 10
     * 12:47:00.600 [     main] future 완료, future=java.util.concurrent.FutureTask@6576fe71[Completed normally]
     */
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(1);

        log("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        log("future 반환, future=%s".formatted(future));

        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get();
        log("future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE");

        log("최종 result = %d".formatted(result));
        log("future 완료, future=%s".formatted(future));

        es.close();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(20);
            log("create value = %d".formatted(value));
            log("Callable 종료");
            return value;
        }
    }
}
