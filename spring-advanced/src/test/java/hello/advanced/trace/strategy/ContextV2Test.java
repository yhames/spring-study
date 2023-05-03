package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    @DisplayName("strategy-v1")
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    @DisplayName("strategy-v2")
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스로직1 실행"));
        context.execute(() -> log.info("비즈니스로직2 실행"));
    }
}
