package spring.reactive.observable;

import java.util.Observable;

/**
 * Iterable과 Observabl은 쌍대성(Duality)을 갖습니다.
 * Iterable은 pull 방식으로 데이터를 가져오지만, Observable은 push 방식으로 데이터를 받아서 사용합니다.
 * 데이터를 가져오는 방식을 보면 Iterable은 iter.next()를 호출하여 데이터를 반환받지만,
 * Observable은 notifyObservers(Object arg)를 호출하여 데이터를 사용자에게 보냅니다.
 * 즉, Iterable은 사용자가 데이터를 가져오는 방식이고, Observable은 데이터를 사용자에게 보내는 방식입니다.
 */
@SuppressWarnings("deprecation")
public class MyObservable extends Observable implements Runnable {

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            setChanged();
            notifyObservers(i);
        }
    }
}
