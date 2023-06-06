package calculator;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringCalculator {

    private String regex = ",|:";

    public int add(String text) {
        if (isBlank(text)) {
            return 0;
        }
        String[] values = parseValues(text);
        int[] numbers = toInt(values);
        return sum(numbers);
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    private int sum(int[] numbers) {
        return IntStream.of(numbers).sum();
    }

    private int[] toInt(String[] values) {
        return Stream.of(values)
                .mapToInt(Integer::parseInt)
                .peek(this::isPositive)
                .toArray();
    }

    private String[] parseValues(String text) {
        if (text.startsWith("//")) {
            addCustomSeparator(text);
        }
        return text.substring(text.indexOf("\n") + 1).split(regex);
    }

    private void addCustomSeparator(String text) {
        String customSeparator = text.substring(text.lastIndexOf("/") + 1, text.indexOf("\n"));
        regex = regex + "|" + customSeparator;
    }

    private void isPositive(int num) {
        if (num < 0) {
            throw new RuntimeException();
        }
    }
}
