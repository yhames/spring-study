package org.example.splearn.application.provided;

import org.example.splearn.domain.Member;

/**
 * 회원 정보를 조회하는 기능을 제공한다.
 */
public interface MemberFinder {

    Member find(Long memberId);

}
