package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorAnswerTest {

    private StringCalculatorAnswer cal;

    @BeforeEach
    void init() {
        cal = new StringCalculatorAnswer();
    }

    @Test
    void add_null_or_empty() {
        assertEquals(cal.add(null), 0);
        assertEquals(cal.add(""), 0);
    }

    @Test
    void add_single_arg() {
        assertEquals(cal.add("1"), 1);
    }

    @Test
    void add_with_comma() {
        assertEquals(cal.add("1,2"), 3);
    }

    @Test
    void add_with_comma_colons() {
        assertEquals(cal.add("1,2:3"), 6);  // TODO
    }
}