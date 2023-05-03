package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RequestMapping // @Controller 혹은 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
@ResponseBody   // HTTP 메시지 컨버터를 사용해서 응답
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    // 인터페이스에서는 자바 버전에 따라서 스프링에서 인식이 잘 안되는 경우가 있어서
    // @RequestParam 과 name 속성을 명시해줘야한다.
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
