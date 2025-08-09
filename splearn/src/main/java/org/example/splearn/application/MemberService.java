package org.example.splearn.application;

import lombok.RequiredArgsConstructor;
import org.example.splearn.application.provided.MemberRegister;
import org.example.splearn.application.required.EmailSender;
import org.example.splearn.application.required.MemberRepository;
import org.example.splearn.domain.Member;
import org.example.splearn.domain.MemberRegisterRequest;
import org.example.splearn.domain.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest request) {
        Member member = Member.register(request, passwordEncoder);
        memberRepository.save(member);
        emailSender.send(member.getEmail(), "회원 가입 완료", "환영합니다! 회원 가입이 완료되었습니다.");
        return member;
    }
}
