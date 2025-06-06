package executor;

import java.util.concurrent.Callable;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableTask implements Callable<Integer> {

    private final String name;
    private final int sleepMs;

    public CallableTask(String name) {
        this.name = name;
        this.sleepMs = 1000;
    }

    public CallableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public Integer call() throws Exception {
        log("%s - 시작".formatted(name));
        sleep(1000);
        log("%s - 종료".formatted(name));
        return sleepMs;
    }
}
