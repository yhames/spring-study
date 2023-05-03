package com.example.core.autowired;

import com.example.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    /**
     * Options
     * 1. @Autowired(required = false)
     * 2. org.springframework.lang.@Nullable
     * 3. Optional<>
     */
    @Test
    @DisplayName("Autowired Option")
    void autowiredOption() throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {
        @Autowired(required = false)
        public void setNoBean(Member noBean) {
            System.out.println("required false = " + noBean);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("@Nullable = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("Optional<> = " + noBean3);
        }
    }
}
