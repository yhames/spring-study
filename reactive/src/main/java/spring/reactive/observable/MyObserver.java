package spring.reactive.observable;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MyObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
