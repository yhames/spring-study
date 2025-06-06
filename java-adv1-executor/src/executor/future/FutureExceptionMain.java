package executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {

    /**
     * 13:49:09.634 [     main] 작업 전달
     * 13:49:09.664 [pool-1-thread-1] Callable 실행, 예외 발생
     * 13:49:10.670 [     main] future.get() 호출 시도, future.state(): FAILED
     * 13:49:10.670 [     main] e = java.util.concurrent.ExecutionException: java.lang.IllegalStateException: ex!
     * 13:49:10.672 [     main] cause = java.lang.IllegalStateException: ex!
     */
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000); // 잠시 대기

        try {
            log("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            log("`InterruptedException`이 아닌 `ExecutionException`이 발생합니다.");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("e = " + e);
            Throwable cause = e.getCause(); // 원본 예외
            log("cause = " + cause);
        }
        es.close();
    }

    static class ExCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}
