package com.example.core.scope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class PrototypeWithSingleton {

    @Test
    @DisplayName("prototypeBeanFind")
    void prototypeBeanFind() throws Exception {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(Prototype.class);
        Prototype prototypeBean1 = ac.getBean(Prototype.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.count).isEqualTo(1);

        Prototype prototypeBean2 = ac.getBean(Prototype.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.count).isEqualTo(1);

    }

    @Test
    @DisplayName("singletonClientProtoytpe")
    void singletonClientPrototype() {
        ConfigurableApplicationContext ac =
                new AnnotationConfigApplicationContext(Prototype.class, ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
//         clientBean이 싱글톤이라 생성시점에 주입된 prototype 빈이 계솏쓰인다.
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);


    }

    @Component
    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean {

        private final Provider<Prototype> prototypeProvider;  // 생성시점에 주입

        public int logic() {
            return prototypeProvider.get().addCount();
        }
    }

    @Component
    @Scope("prototype")
    @Getter
    static class Prototype {
        private int count = 0;

        public int addCount() {
            count++;
            return this.getCount();
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close");
        }
    }
}
