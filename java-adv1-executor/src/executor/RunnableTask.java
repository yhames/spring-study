package executor;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableTask implements Runnable {

    private final String name;
    private final int sleepMs;

    public RunnableTask(String name) {
        this.name = name;
        this.sleepMs = 1000;
    }

    public RunnableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        log("%s - 시작".formatted(name));
        sleep(sleepMs);
        log("%s - 종료".formatted(name));
    }
}
