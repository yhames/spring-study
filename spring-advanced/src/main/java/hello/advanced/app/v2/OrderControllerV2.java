package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 트랜잭션 ID, Level 표현을 위해 TraceId(id, level) 매개변수로 전달하여 동기화
 * X TraceId 동기화를 위해 모든 메서드를 다 수정해야함, 인터페이스까지...!!!
 * X 컨트롤러 제외한 서비스나 리포지토리에는 begin() 없음.
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV2.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;    // 예외를 반드시 다시 넘겨줘야한다.
        }
    }
}
