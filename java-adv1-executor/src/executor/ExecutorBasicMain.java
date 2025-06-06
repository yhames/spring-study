package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {

    /**
     * 12:46:14.210 [     main] == 초기상태 ==
     * 12:46:14.214 [     main] [poolSize=0, activeCount=0, queuedTaskCount=0, completedTaskCount=0]
     * 12:46:14.215 [     main] == 작업수행중 ==
     * 12:46:14.216 [     main] [poolSize=2, activeCount=2, queuedTaskCount=2, completedTaskCount=0]
     * 12:46:14.216 [pool-1-thread-2] taskB - 시작
     * 12:46:14.216 [pool-1-thread-1] taskA - 시작
     * 12:46:15.230 [pool-1-thread-2] taskB - 종료
     * 12:46:15.230 [pool-1-thread-1] taskA - 종료
     * 12:46:15.230 [pool-1-thread-2] taskC - 시작
     * 12:46:15.230 [pool-1-thread-1] taskD - 시작
     * 12:46:16.236 [pool-1-thread-2] taskC - 종료
     * 12:46:16.236 [pool-1-thread-1] taskD - 종료
     * 12:46:17.225 [     main] == 작업수행완료 ==
     * 12:46:17.225 [     main] [poolSize=2, activeCount=0, queuedTaskCount=0, completedTaskCount=4]
     * 12:46:17.227 [     main] == shutdown 완료 ==
     * 12:46:17.227 [     main] [poolSize=0, activeCount=0, queuedTaskCount=0, completedTaskCount=4]
     */
    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());
        log("== 초기상태 ==");
        printState(es);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        log("== 작업수행중 ==");
        printState(es);

        sleep(3000); // 3초 대기

        log("== 작업수행완료 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }
}
