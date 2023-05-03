package hello.advanced.trace.strategy.code;

import hello.advanced.trace.strategy.code.template.CallBack;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    /**
     * 템플릿 콜백 패턴 - 익명클래스
     */
    @Test
    @DisplayName("callbackV1")
    void callBackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new CallBack() {
            @Override
            public void call() {
                log.info("비즈니스로직1 실행");
            }
        });
        template.execute(new CallBack() {
            @Override
            public void call() {
                log.info("비즈니스로직2 실행");
            }
        });
    }

    /**
     * 템플릿 콜백 패턴 - 람다
     */
    @Test
    @DisplayName("callbackV2")
    void callBackV2() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스로직1 실행"));
        template.execute(() -> log.info("비즈니스로직2 실행"));
    }
}
