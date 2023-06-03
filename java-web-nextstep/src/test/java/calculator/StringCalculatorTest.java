package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

    private StringCalculator cal;

    @BeforeEach
    void init() {
        cal = new StringCalculator();
    }

    @Test
    @DisplayName("기본 구분자 - 1개")
    void singleArg() {
        String sc = "1";
        assertEquals(cal.add(sc), 1);
    }

    @Test
    @DisplayName("기본 구분자 - 2개")
    void defaultSeparatorArg() {
        String sc = "1,2";
        assertEquals(cal.add(sc), 3);
    }

    @Test
    @DisplayName("기본 구분자 - 3개")
    void defaultSeparatorArgs() {
        String sc = "1,2:3";
        assertEquals(cal.add(sc), 6);
    }

    @Test
    @DisplayName("커스텀 구분자")
    void customSeparator() {
        String sc = "//;\n1;2;3";
        assertEquals(cal.add(sc), 6);
    }

    @Test
    @DisplayName("공백")
    void EmptyArgs() {
        String sc = " ";
        assertEquals(cal.add(sc), 0);
    }

    @Test
    @DisplayName("빈문자열")
    void NoArgs() {
        String sc = "";
        assertEquals(cal.add(sc), 0);
    }

    @Test
    @DisplayName("null")
    void nullArgs() {
        String sc = null;
        assertEquals(cal.add(sc), 0);
    }

    @Test
    @DisplayName("음수")
    void negativeArgs() {
        String sc = "-1,2,3";
        assertThrows(RuntimeException.class, () -> cal.add(sc));
    }
}