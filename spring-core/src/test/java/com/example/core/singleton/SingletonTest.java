package com.example.core.singleton;

import com.example.core.AppConfig;
import com.example.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("순수 DI 컨테이너")
    void pureContainer() throws Exception {
        AppConfig appConfig = new AppConfig();

        // 호출할 때마다 객체를 생성하는지 확인
        MemberService memberService = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService = " + memberService);
        System.out.println("memberService2 = " + memberService2);

        // memberService != memberService2
        Assertions.assertThat(memberService).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴 적용 객체 사용")
    void SingletonServiceTest() throws Exception {
        // given
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        // expected
        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);
        Assertions.assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("스프링 컨테이너 싱글톤")
    void springContainer() throws Exception {
        // given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // 호출할 때마다 객체를 생성하는지 확인
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // expected
        System.out.println("memberService = " + memberService);
        System.out.println("memberService2 = " + memberService2);
        // memberService != memberService2
        Assertions.assertThat(memberService).isSameAs(memberService2);
    }
}
