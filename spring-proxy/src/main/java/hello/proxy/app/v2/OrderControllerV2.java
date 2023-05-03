package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping
@ResponseBody
//@Controller // 주의, @Component 가 포함되어 있어서 컴포넌트스캔으로 자동주입 대상이 됨.
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.order(itemId);
        return "OK";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "OK";
    }
}
