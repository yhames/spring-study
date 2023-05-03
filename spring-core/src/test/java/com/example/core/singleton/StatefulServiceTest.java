package com.example.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    @DisplayName("상태를 유지하는 필드")
    void statefulField() throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // ThreadA : 사용자 userA 10000원 주문
        statefulService.order("userA", 10000);
        // ThreadB : 사용자 userB 20000원 주문
        statefulService2.order("userB", 20000);

        Assertions.assertThat(statefulService.getPrice()).isNotEqualTo(10000);
        Assertions.assertThat(statefulService.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}