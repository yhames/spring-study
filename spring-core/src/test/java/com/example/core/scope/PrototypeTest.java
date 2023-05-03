package com.example.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    @DisplayName("prototypeBeanFind")
    void prototypeBeanFind() throws Exception {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find PrototypeBean");
        PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
        System.out.println("find PrototypeBean");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class); // 초기화메서드 2번 호출됨
        System.out.println("prototypeBean = " + prototypeBean);
        System.out.println("prototypeBean2 = " + prototypeBean2);   // 서로 다름
        Assertions.assertThat(prototypeBean).isNotSameAs(prototypeBean2);
        ac.close(); // 호출안됨.
    }

    @Component
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("prototypeBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("prototypeBean.close");
        }
    }
}
