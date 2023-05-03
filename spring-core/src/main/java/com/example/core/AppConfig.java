package com.example.core;

import com.example.core.discount.DiscountPolicy;
import com.example.core.discount.RateDiscountPolicy;
import com.example.core.member.MemberRepository;
import com.example.core.member.MemberService;
import com.example.core.member.MemberServiceImpl;
import com.example.core.member.MemoryMemberRepository;
import com.example.core.order.OrderService;
import com.example.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // CGLIB 바이트조작 라이브러리를 사용하여 AppConfig 를 상속받는 다른 클래스를 만들어 빈으로 등록한다.
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * By marking this method as static, it can be invoked without causing instantiation of its declaring @Configuration class, thus avoiding the above-mentioned lifecycle conflicts. Note however that static @Bean methods will not be enhanced for scoping and AOP semantics as mentioned above. This works out in BFPP cases, as they are not typically referenced by other @Bean methods. As a reminder, an INFO-level log message will be issued for any non-static @Bean methods having a return type assignable to BeanFactoryPostProcessor.
     * To sum up, @Configuration 클래스가 인스턴스화 되기 전에 static 으로 선언된 클래스가 먼저 호출되기 때문이다.
     */
//    @Bean
//    public static MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public static DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
