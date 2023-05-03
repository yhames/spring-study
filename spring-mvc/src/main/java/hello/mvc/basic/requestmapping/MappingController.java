package hello.mvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 요청매핑 기본 사용방법
 */
@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 기본적인 사용방법, GET,POST 등 모든 방식 허용
     */
    @RequestMapping("/hello-basic")
    public String basicHello() {
        log.info("basicHello");
        return "OK";
    }

    /**
     * Method 지정
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGet() {
        log.info("mappingGet");
        return "OK";
    }

    /**
     * Method 지정 어노테이션 버전
     */
    @PostMapping(value = "/mapping-get-v2")
    public String mappingPost() {
        log.info("mappingPost");
        return "OK";
    }

    /**
     * PathVariable(경로변수), GET 방식의 쿼리파라미터와는 다름.
     * //
     */
//    @GetMapping("/mapping/{userId}")
//    public String mappingPath(@PathVariable("userId") String data) {  // 변수명과 경로명이 다르면 경로명 지정해줘야하고
//        log.info("mappingPath userId={}", data);
//        return "OK";
//    }
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {    // 변수명과 경로명이 같으면 그냥 써도 됨
        log.info("mappingPath userId={}", userId);
        return "OK";
    }

    /**
     * PathVariable 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "OK";
    }

    /**
     * 참고
     * 1. 특정 파라미터 기반 추가 매핑
     * 2. 특정 헤더 기반 추가 매핑
     * 3. content-type 헤더 기반 추가 매핑 - consumes
     * 4. accept 헤더 기반 추가 매핑 - produces
     */
//    @GetMapping(value = "/mapping-param", params = "mode=debug")
//    @GetMapping(value = "/mapping-headers", headers = "mode=debug")
//    @GetMapping(value = "/mapping-consumes", consumes = "application/json") // 헤더가 아니라 consumes 사용
//    @GetMapping(value = "/mapping-produces", produces = "text/html") // 헤더가 아니라 produces 사용

}
