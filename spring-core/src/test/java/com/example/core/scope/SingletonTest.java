package com.example.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    @DisplayName("singletonBeanFind")
    void singletonBeanFind() throws Exception {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        System.out.println("find SingletonBean");   // init 메서드가 20번째 라인에서 먼저 호출됨
        SingletonBean singletonBean = ac.getBean(SingletonBean.class);  // 더이상 초기화를 수행하지 않음
        System.out.println("find SingletonBean");
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class); // 더이상 초기화를 수행하지 않음
        System.out.println("singletonBean = " + singletonBean);
        System.out.println("singletonBean2 = " + singletonBean2);   // 서로 같음
        Assertions.assertThat(singletonBean).isSameAs(singletonBean2);
        ac.close(); // 종료 메서드 수행함.
    }

    @Component
    @Scope("singleton")
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("singletonBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("singletonBean.close");
        }
    }
}
