package org.example.splearn.application.member.provided;

import org.example.splearn.domain.member.Member;

/**
 * 회원 정보를 조회하는 기능을 제공한다.
 */
public interface MemberFinder {

    Member find(Long memberId);

}
