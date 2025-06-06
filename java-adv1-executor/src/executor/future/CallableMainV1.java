package executor.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV1 {

    /**
     * 12:45:22.567 [pool-1-thread-1] Callable 시작
     * 12:45:24.591 [pool-1-thread-1] create value = 13
     * 12:45:24.591 [pool-1-thread-1] Callable 종료
     * 12:45:24.591 [     main] 최종 result = 13
     */
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        log("최종 result = %d".formatted(result));
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
