package executor.future;

import executor.CallableTask;

import java.util.List;
import java.util.concurrent.*;

import static util.MyLogger.log;

public class InvokeAllMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask callableTask1 = new CallableTask("task1", 1000);
        CallableTask callableTask2 = new CallableTask("task2", 2000);
        CallableTask callableTask3 = new CallableTask("task3", 3000);

        List<CallableTask> tasks = List.of(callableTask1, callableTask2, callableTask3);
        List<Future<Integer>> futures = es.invokeAll(tasks);
        for (Future<Integer> future : futures) {
            Integer value = future.get();
            log("CallableTask result: " + value);
        }
        es.close();
    }
}
