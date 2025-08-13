package org.example.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.example.splearn.domain.member.Member;
import org.example.splearn.domain.member.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static org.example.splearn.domain.member.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member);
        em.flush();

        assertThat(member.getId()).isNotNull();
        em.flush();
        em.clear();

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(foundMember.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(foundMember.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        Member duplicateMember = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(duplicateMember))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}