package com.example.core.scan;

import com.example.core.AutoAppConfig;
import com.example.core.member.MemberService;
import com.example.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
    
    @Test
    @DisplayName("basic scan")
    void basicScan() throws Exception {
        // given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);

        // expected
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        
    }
}
