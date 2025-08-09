package org.example.splearn.application.provided;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.splearn.SplearnTestConfiguration;
import org.example.splearn.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import({SplearnTestConfiguration.class})
public record MemberRegisterTest(MemberRegister memberRegister) {

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

}
