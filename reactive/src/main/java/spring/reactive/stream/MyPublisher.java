package spring.reactive.stream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class MyPublisher implements Publisher<Integer> {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new MySubscription(subscriber, executor));
    }
}
