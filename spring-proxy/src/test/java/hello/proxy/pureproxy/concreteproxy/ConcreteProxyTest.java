package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    @DisplayName("no-proxy-test")
    void noProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(logic);
        client.execute();
    }

    @Test
    @DisplayName("time-proxy-test")
    void timeProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        TimeProxy proxy = new TimeProxy(logic);
        ConcreteClient client = new ConcreteClient(proxy);
        client.execute();
    }
}
