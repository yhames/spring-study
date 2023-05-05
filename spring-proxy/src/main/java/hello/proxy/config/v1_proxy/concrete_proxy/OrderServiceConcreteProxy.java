package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        // 자식클래스의 객체가 생성될 때 부모클래스의 객체도 생성된다.
        // 이는 자식클래스에서 부모클래스의 필드 혹은 메서드를 사용할 수 있다는 것을 생각하면 자연스럽다.
        // 하지만 부모클래스의 생성자는 상속되지 않는다.
        // 따라서 자식클래스에서는 부모클래스의 생성자를 호출해줘야한다.
        // 부모클래스 생성자 호출 생략시 기본생성자(`super()`)가 호출된다.
        super(null);    // 부모클래스의 기능을 사용하지 않고 target 으로 받아서 사용하기 때문에 null 로 생성
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void order(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.order(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
