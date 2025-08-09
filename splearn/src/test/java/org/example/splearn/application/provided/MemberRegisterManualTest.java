package org.example.splearn.application.provided;

import org.example.splearn.application.MemberService;
import org.example.splearn.application.required.EmailSender;
import org.example.splearn.application.required.MemberRepository;
import org.example.splearn.domain.Email;
import org.example.splearn.domain.Member;
import org.example.splearn.domain.MemberFixture;
import org.example.splearn.domain.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MemberRegisterManualTest {

    @Test
    void registerTestStub() {
        MemberService memberService = new MemberService(
                new MemberRepositoryStub(),
                new EmailSenderStub(),
                MemberFixture.createPasswordEncoder()
        );
        Member member = memberService.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSender = new EmailSenderMock();
        MemberService memberService = new MemberService(
                new MemberRepositoryStub(),
                emailSender,
                MemberFixture.createPasswordEncoder()
        );
        Member member = memberService.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(emailSender.to).hasSize(1);
        assertThat(emailSender.to.getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    void registerTestMockito() {
        EmailSender emailSender = mock(EmailSender.class);
        MemberService memberService = new MemberService(
                new MemberRepositoryStub(),
                emailSender,
                MemberFixture.createPasswordEncoder()
        );
        Member member = memberService.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        verify(emailSender, times(1)).send(eq(member.getEmail()), any(), any());
    }

    static class MemberRepositoryStub implements MemberRepository {

        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {
        }
    }

    static class EmailSenderMock implements EmailSender {

        List<Email> to = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            to.add(email);
        }
    }

}