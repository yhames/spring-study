package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DBConnectionUtilTest {

    @Test
    @DisplayName("connection")
    void connection() throws Exception {
        // given
        Connection connection = DBConnectionUtil.getConnection();

        // expected
        assertThat(connection).isNotNull();
    }

}