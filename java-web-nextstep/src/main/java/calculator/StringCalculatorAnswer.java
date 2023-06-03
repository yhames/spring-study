package calculator;

import java.util.stream.Stream;

public class StringCalculatorAnswer {

    public int add(String text) {
        if (isBlank(text)) {
            return 0;
        }
        String[] values = text.split(",");
        return sum(toInts(values));
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    private int[] toInts(String[] values) {
        int[] numbers = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            numbers[i] = Integer.parseInt(values[i]);
        }
        return numbers;
    }

    private int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

}
