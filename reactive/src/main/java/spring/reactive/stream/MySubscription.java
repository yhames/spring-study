package spring.reactive.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscription implements Subscription {
    private final Iterator<Integer> it;

    private final Subscriber<? super Integer> subscriber;

    private final ExecutorService executor;

    MySubscription(Subscriber<? super Integer> subscriber, ExecutorService executor) {
        this.subscriber = subscriber;
        this.executor = executor;
        this.it = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).iterator();
    }

    @Override
    public void request(long n) {
        executor.execute(() -> {
            int i = 0;
            try {
                while (i++ < n) {
                    if (it.hasNext()) {
                        subscriber.onNext(it.next());
                    } else {
                        subscriber.onComplete();
                        break;
                    }
                }
            } catch (RuntimeException e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    public void cancel() {

    }
}
