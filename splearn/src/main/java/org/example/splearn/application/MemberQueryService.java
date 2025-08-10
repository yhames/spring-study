package org.example.splearn.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.splearn.application.provided.MemberFinder;
import org.example.splearn.application.required.MemberRepository;
import org.example.splearn.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다: " + memberId));
    }

}
