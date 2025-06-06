package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int poolSize = poolExecutor.getPoolSize();
            int activeCount = poolExecutor.getActiveCount();
            int queuedTaskCount = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log("[poolSize=%s, activeCount=%s, queuedTaskCount=%s, completedTaskCount=%s]"
                    .formatted(poolSize, activeCount, queuedTaskCount, completedTaskCount));
        } else {
            log(executorService);
        }
    }
}
