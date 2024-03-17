package spring.reactive.stream;

import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.Flow.Subscription;

public class MySubscriber<T> implements Subscriber<T> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println(Thread.currentThread() +  "onSubscribe");
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println(Thread.currentThread() + " onNext " + item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError" + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
