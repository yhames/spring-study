package spring.reactive.observable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyObservableTest {

    @Test
    @DisplayName("Observable 테스트")
    void observableTest() {
        MyObservable observable = new MyObservable();
        MyObserver observer = new MyObserver();
        observable.addObserver(observer);
        observable.run();
    }
}