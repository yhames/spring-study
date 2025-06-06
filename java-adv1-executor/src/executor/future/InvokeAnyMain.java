package executor.future;

import executor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.MyLogger.log;

public class InvokeAnyMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask callableTask1 = new CallableTask("task1", 1000);
        CallableTask callableTask2 = new CallableTask("task2", 2000);
        CallableTask callableTask3 = new CallableTask("task3", 3000);

        List<CallableTask> tasks = List.of(callableTask1, callableTask2, callableTask3);
        Integer value = es.invokeAny(tasks);
        log("CallableTask result: " + value);
        es.close();
    }
}
