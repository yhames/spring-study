package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - 트랜잭션 사용을 위한 Connection Param 추가
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {
        String sql = "insert into member (member_id, money) values (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null; // Statement 자식타입. SQL Injection 공격 예방 가능.

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());   // 1번째 ?에 값을 지정
            pstmt.setInt(2, member.getMoney()); // 2번째 ?에 값을 지정
            pstmt.executeUpdate();  // Statement를 통해 준비된 sql을 connection을 통해 실제 데이터베이스에 전달, 영향을 받은 row 수 반환
            return member;
        } catch (SQLException e) {
            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(conn, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();  // 데이터를 변경하지 않으면 executeQuery, 변경하면 executeUpdate
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId =" + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(conn, pstmt, rs);
        }
    }
    public Member findById(Connection conn, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

//        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
//            conn = getConnection();    // 서비스에서 같은 세션을 유지하기 위해 새로운 커넥션 연결하면 안됨
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();  // 데이터를 변경하지 않으면 executeQuery, 변경하면 executeUpdate
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId =" + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
//            close(conn, pstmt, rs);  // 서비스에서 사용해야하기 때문에 커넥션 닫으면 안됨
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);   // 1번째 ?에 값을 지정
            pstmt.setString(2, memberId); // 2번째 ?에 값을 지정
            int resultSize = pstmt.executeUpdate(); // 영향받은 row 수
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(conn, pstmt, null);
        }
    }

    public void update(Connection conn, String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);   // 1번째 ?에 값을 지정
            pstmt.setString(2, memberId); // 2번째 ?에 값을 지정
            int resultSize = pstmt.executeUpdate(); // 영향받은 row 수
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
//            close(conn, pstmt, null);
            JdbcUtils.closeStatement(pstmt);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);   // 1번째 ?에 값을 지정
            pstmt.executeUpdate();
        } catch (SQLException e) {


            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        // 종료순서 ResultSet -> Statement -> Connection
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(conn);    // 커넥션 풀 사용시 연결이 종료되는 것이 아니라 커넥션 풀에 반환
    }

    private Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        log.info("get connection={}, class={}", conn, conn.getClass());
        return conn;
    }
}
