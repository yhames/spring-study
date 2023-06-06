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
        assertEquals(0, cal.add(null));
        assertEquals(0, cal.add(""));
    }

    @Test
    void add_single_arg() {
        assertEquals(1, cal.add("1"));
    }

    @Test
    void add_with_comma() {
        assertEquals(3, cal.add("1,2"));
    }

    @Test
    void add_with_comma_colons() {
        assertEquals(6, cal.add("1,2:3"));
    }

    @Test
    void add_custom_separator() {
        assertEquals(6, cal.add("//;\n1;2;3"));
    }

    @Test
    void add_negative() {
        assertThrows(RuntimeException.class, () -> cal.add("-1,2,3"));
    }
}