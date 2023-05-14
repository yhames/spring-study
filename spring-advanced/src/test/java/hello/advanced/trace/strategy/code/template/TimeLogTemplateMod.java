package hello.advanced.trace.strategy.code.template;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 입문을 위한 자바 객체지향 원리와 이해 p.229-230 - 리펙토링된 템플릿 콜백 패턴
 */
@Slf4j
public class TimeLogTemplateMod {

    public void execute(String logText) {
        long startTime = System.currentTimeMillis();
        executeCallBack(logText).call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    /**
     * 익명 클래스(혹은 람다)를 컨텍스트로 이관
     */
    private CallBack executeCallBack(final String logText) {
        return new CallBack() {
            @Override
            public void call() {
                log.info(logText);
            }
        };
    }
}
