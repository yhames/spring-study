package spring.reactive.stream;

import org.junit.jupiter.api.Test;

public class MyPubSubTest {

    @Test
    void pubSubTest() {
        MyPublisher<Integer> pub = new MyPublisher<>();
        MySubscriber<Integer> sub = new MySubscriber<>();
        MySubscriber<Integer> sub2 = new MySubscriber<>();
        pub.subscribe(sub);
        pub.subscribe(sub2);
    }
}
