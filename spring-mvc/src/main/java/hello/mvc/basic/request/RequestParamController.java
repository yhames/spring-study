package hello.mvc.basic.request;

import hello.mvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("OK");
    }

    @ResponseBody   // view 무시하고 메시지바디에 문자열 넣어서 반환
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "OK";
    }

    /**
     * 이름 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * 다 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {    // 명시적으로 표현해주는게 좋을것같은데 어떤식으로 사용할지 모르겠네
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam String username,
                                       @RequestParam(required = false) Integer age) {   // 기본형 int 는 null 값을 가질 수 없어서 Integer 사용해야함
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
                                      @RequestParam(defaultValue = "-1") int age) {
        /**
         * 빈 문자("")의 경우에도 기본값이 들어감
         */
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }

//    @ResponseBody
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttribute(@RequestParam String username, @RequestParam int age) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//        log.info("helloData={}", helloData);
//        return "OK";
//    }

    /**
     * ModelAttribute : 요청파라미터를 객체 자동 바인딩
     * 1. 객체생성(HelloData helloData)
     * 2. 요청파라미터 이름으로 객체의 프로퍼티(setUsername, setAge -> username, age)를 찾는다.
     * 3. 조회된 프로퍼티(setter)를 호출하여 파리미터 값 바인딩(입력), 자료형이 안맞으면 BindException 발생!
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("helloData={}", helloData);
        return "OK";
    }

    /**
     * String, int, Integer 등 단순 타입 -> @RequestParam
     * 그 외 객체 -> @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("helloData={}", helloData);
        return "OK";
    }
}
