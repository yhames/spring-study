package spring.reactive.iterable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyIterableTest {

    @Test
    @DisplayName("Iterator 테스트")
    void iteratorTest() {
        MyIterable iter = new MyIterable();
        for (int i : iter) {
            System.out.println(i);
        }
    }
}