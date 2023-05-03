package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManger 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member (member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null; // Statement 자식타입. SQL Injection 공격 예방 가능.

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());   // 1번째 ?에 값을 지정
            pstmt.setInt(2, member.getMoney()); // 2번째 ?에 값을 지정
            pstmt.executeUpdate();  // Statement를 통해 준비된 sql을 connection을 통해 실제 데이터베이스에 전달, 영향을 받은 row 수 반환
            return member;
        } catch (SQLException e) {
            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
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
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);   // 1번째 ?에 값을 지정
            pstmt.setString(2, memberId); // 2번째 ?에 값을 지정
            int resultSize = pstmt.executeUpdate(); // 영향받은 row 수
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);   // 1번째 ?에 값을 지정
            pstmt.executeUpdate();
        } catch (SQLException e) {


            log.error("db error", e);   // 로그 정보 남기고
            throw e;    // 예외 그냥 던진다.
        } finally {
            close(con, pstmt, rs);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        // 종료순서 ResultSet -> Statement -> Connection
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
