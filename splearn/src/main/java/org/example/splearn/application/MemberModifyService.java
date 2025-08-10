package org.example.splearn.application;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.splearn.application.provided.MemberFinder;
import org.example.splearn.application.provided.MemberRegister;
import org.example.splearn.application.required.EmailSender;
import org.example.splearn.application.required.MemberRepository;
import org.example.splearn.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(@Valid MemberRegisterRequest request) {
        checkDuplicateEmail(request);
        Member member = Member.register(request, passwordEncoder);
        memberRepository.save(member);
        sendWelcomeEmail(member);
        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.activate();
        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "회원 가입 완료", "환영합니다! 회원 가입이 완료되었습니다.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest request) {
        if (memberRepository.findByEmail(new Email(request.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다: " + request.email());
        }
    }

}
