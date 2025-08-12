package org.example.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.splearn.SplearnTestConfiguration;
import org.example.splearn.domain.*;
import org.example.splearn.domain.member.DuplicateEmailException;
import org.example.splearn.domain.member.Member;
import org.example.splearn.domain.member.MemberRegisterRequest;
import org.example.splearn.domain.member.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import({SplearnTestConfiguration.class})
public record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        assertThatThrownBy(() -> memberRegister.register(new MemberRegisterRequest("test@test.com", "no", "123456789")))
                .isInstanceOf(ConstraintViolationException.class);
        assertThatThrownBy(() -> memberRegister.register(new MemberRegisterRequest("test@test.com", "too-looooooooooooooooong", "123456789")))
                .isInstanceOf(ConstraintViolationException.class);
        assertThatThrownBy(() -> memberRegister.register(new MemberRegisterRequest("test@test.com", "test123", "12345")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member activated = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(activated.getId()).isEqualTo(member.getId());
        assertThat(activated.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

}
