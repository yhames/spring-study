package hello.proxy.pureproxy.decorater;

import hello.proxy.pureproxy.decorater.code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {

    @Test
    @DisplayName("no-decorator-test")
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }
    
    @Test
    @DisplayName("msg-decorator-test")
    void messageDecoratorTest() {
        Component component = new RealComponent();
        MessageDecorator msgDeco = new MessageDecorator(component);
        DecoratorPatternClient client = new DecoratorPatternClient(msgDeco);
        client.execute();
    }
    
    @Test
    @DisplayName("time-msg-decorator-test")
    void timeMsgDecoratorTest() {
        Component component = new RealComponent();
        MessageDecorator msgDeco = new MessageDecorator(component);
        TimeDecorator timeDeco = new TimeDecorator(msgDeco);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDeco);
        client.execute();
    }
}