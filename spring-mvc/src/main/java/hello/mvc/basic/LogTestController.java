package hello.mvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그 사용시 장점
 * 1. 부가정보(쓰레드 정보, 클래스 이름 등) 획득 가능
 * 2. 개발서버와 운영서버 로그 레벨 설정 가능
 * 3. 파일 출력 가능
 * 4. System.out 보다 성능 좋음
 */
//@Controller -> 뷰 반환
@RestController // -> response 메시지바디에 문자열 바로 입력
@Slf4j  // Logger 자동으로 불러옴
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        System.out.println("===== [log test] - start =====");
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);
//        log.debug("debug log=" + name);   // 이렇게 하면 출력되지 않는 로그도 문자열 연산이 일어나서 사용하면 안됨.
        System.out.println("===== [log test] - end =====");

        return "OK";
    }
}
