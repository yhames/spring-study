package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    @DisplayName("strategy-v0")
    void strategyV0() {
        logic1();
        logic2();
    }

    /**
     * 전략패턴 사용
     */
    @Test
    @DisplayName("strategy-v1-전략패턴")
    void strategyV1() {
        Strategy strategy1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        Strategy strategy2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategy2);
        context2.execute();
    }

    /**
     * 익명 내부 클래스 사용
     */
    @Test
    @DisplayName("strategy-v2-익명내부클래스")
    void strategyV2() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);
        log.info("strategy1={}", strategy1.getClass());
        context1.execute();

        Strategy strategy2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직2 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategy2);
        log.info("strategy2={}", strategy2.getClass());
        context2.execute();
    }

    /**
     * 익명 내부 클래스 사용 - inline
     */
    @Test
    @DisplayName("strategy-v3-익명내부클래스_inline")
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * 익명 내부 클래스 - lamda - 인터페이스에 메서드가 1개만 있어야함
     */
    @Test
    @DisplayName("strategy-v4-익명내부클래스_lamda")   // 람다를 쓰려면 인터페이스에 메서드가 1개만 있어야한다.
    void strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스로직1 실행"));   // 선조립
        context1.execute(); // 후실행

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스로직2 실행"));
        context2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        // 비즈니스로직
        log.info("비즈니스로직1 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직
        log.info("비즈니스로직2 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }
}
