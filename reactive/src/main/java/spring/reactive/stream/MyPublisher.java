package spring.reactive.stream;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class MyPublisher<T> implements Publisher<T> {
    @Override
    public void subscribe(Subscriber subscriber) {
        subscriber.onSubscribe(new MySubscription(subscriber));
    }
}
