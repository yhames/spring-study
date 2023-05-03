package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀 고려 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            // 트랜잭션 시작
            conn.setAutoCommit(false);
            // 비즈니스 로직
            bizLogic(conn, fromId, toId, money);
            // 성공 시 커밋
            conn.commit();
        } catch (Exception e) {
            // 실패 시 롤백
            conn.rollback();
            throw new IllegalStateException();
        } finally {
            release(conn);
        }
    }

    private void bizLogic(Connection conn, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(conn, fromId);
        Member toMember = memberRepository.findById(conn, toId);

        memberRepository.update(conn, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(conn, toId, toMember.getMoney() + money);
    }

    private void release(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);   // 종료되는 것이 아니라 커넥션풀에 반환하는것이기 때문에 커넥션풀 고려 자동커밋모드로 전환
                conn.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
