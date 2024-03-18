package spring.reactive.stream;

import org.junit.jupiter.api.Test;

public class MyPubSubTest {

    @Test
    void pubSubTest() {
        MyPublisher pub = new MyPublisher();
        MySubscriber sub = new MySubscriber();
        MySubscriber sub2 = new MySubscriber();
        pub.subscribe(sub);
        pub.subscribe(sub2);
    }
}
