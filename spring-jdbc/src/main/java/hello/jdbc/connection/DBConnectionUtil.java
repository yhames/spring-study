package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        /**
         * 데이터베이스에 미리 Member table 추가해야함.
         * drop table member if exists cascade;
         *     create table member (
         *         member_id varchar(10),
         *         money integer not null default 0,
         *         primary key (member_id)
         * );
         */
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
}
