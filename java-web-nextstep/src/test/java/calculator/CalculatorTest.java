package calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void before() {
        calculator = new Calculator();
        System.out.println("before");
    }

    @AfterEach
    public void after() {
        System.out.println("after");
    }

    @Test
    public void add() {
        assertEquals(9, calculator.add(6,3));
        System.out.println("add");
    }

    @Test
    public void subtract() {
        assertEquals(3, calculator.subtract(6,3));
        System.out.println("subtract");
    }

    @Test
    public void multiply() {
        assertEquals(18, calculator.multiply(6,3));
        System.out.println("multiply");
    }

    @Test
    public void divide() {
        assertEquals(2, calculator.divide(6,3));
        System.out.println("divide");
    }
}