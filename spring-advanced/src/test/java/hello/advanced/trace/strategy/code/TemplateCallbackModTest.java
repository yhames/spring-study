package hello.advanced.trace.strategy.code;

import hello.advanced.trace.strategy.code.template.CallBack;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import hello.advanced.trace.strategy.code.template.TimeLogTemplateMod;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 스프링 입문을 위한 자바 객체지향 원리와 이해 p.229-230 - 리펙토링된 템플릿 콜백 패턴
 */
@Slf4j
public class TemplateCallbackModTest {

    /**
     * 리펙토링된 템플릿콜백 패턴 - 익명클래스(혹은 람다)를 컨텍스트로 이관하여
     * 클라이언트에서는 텍스트만 컨텍스트로 전달하면 됨.
     */
    @Test
    @DisplayName("리펙토링된 템플릿콜백 패턴")
    void callBackV2() {
        TimeLogTemplateMod template = new TimeLogTemplateMod();
        template.execute("비즈니스로직1 실행");
        template.execute("비즈니스로직2 실행");
    }
}
