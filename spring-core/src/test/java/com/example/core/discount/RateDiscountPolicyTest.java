package com.example.core.discount;

import com.example.core.member.Grade;
import com.example.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 10% 할인정책 적용")
    void test1() throws Exception {
        // given
        Member member = new Member.builder()
                .id(1L)
                .name("memberA")
                .grade(Grade.VIP)
                .build();

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("BASIC 10% 할인정책 미적용")
    void test2() throws Exception {
        // given
        Member member = new Member.builder()
                .id(1L)
                .name("memberA")
                .grade(Grade.BASIC)
                .build();

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(0);
    }
}