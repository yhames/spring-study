package org.example.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static org.example.splearn.domain.MemberFixture.createPasswordEncoder;

/**
 * - 도메인 모델에 대한 테스트는 외부 의존성을 최소화해서 변경에 유연하고 실행속도가 빠르게 유지한다.
 * - 테스트 코드는 유지보수하는 코드가 아니기 때문에 세부적인 내용을 공을 들여 기술할 필요는 없다.
 */
class MemberTest {

    Member member;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest();
        member = Member.register(memberRegisterRequest, passwordEncoder);
    }


    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail1() {
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivateFail2() {
        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("12345678", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        String before = member.getNickname();
        member.activate();

        member.changeNickname("newNickname");

        assertThat(member.getNickname()).isNotEqualTo(before);
        assertThat(member.getNickname()).isEqualTo("newNickname");
    }

    @Test
    void changePassword() {
        String before = member.getPasswordHash();
        String newPassword = "newSecret";
        member.activate();

        member.changePassword(newPassword, passwordEncoder);

        assertThat(member.getPasswordHash()).isNotEqualTo(before);
        assertThat(member.verifyPassword(newPassword, passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest("invalid-email");

        assertThatThrownBy(() -> Member.register(memberRegisterRequest, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);


        MemberRegisterRequest valid = createMemberRegisterRequest();
        Member.register(valid, passwordEncoder);
    }

}