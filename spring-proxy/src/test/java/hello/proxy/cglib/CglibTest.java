package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    @DisplayName("cglib-test")
    void cglibTest() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer(); // 프록시를 생성하는 Enhancer 객체 생성
        enhancer.setSuperclass(ConcreteService.class);  // 어떤 구체 클래스를 상속받을지 지정
        enhancer.setCallback(new TimeMethodInterceptor(target));    // 프록시에 적용할 로직 할당
        ConcreteService proxy = (ConcreteService) enhancer.create();    // 프록시 생성
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.call();
    }


}
