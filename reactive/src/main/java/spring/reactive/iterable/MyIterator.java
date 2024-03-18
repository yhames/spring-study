package spring.reactive.iterable;

import java.util.Iterator;

public class MyIterator implements Iterator<Integer> {
    int i = 0;

    static final int MAX = 10;

    @Override
    public boolean hasNext() {
        return i < MAX;
    }

    @Override
    public Integer next() {
        return ++i;
    }
}
