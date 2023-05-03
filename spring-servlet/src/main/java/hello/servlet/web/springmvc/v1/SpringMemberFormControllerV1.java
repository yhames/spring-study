package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller
 * 1. 스프링 빈 등록
 * 2. 어노테이션 기반 컨트롤러로 인식 : 클래스 레벨에 @Controller 혹은 @RequestMapping 어노테이션 있을 시
 */
//@Component
//@RequestMapping
@Controller // 위 두가지 어노테이션의 기능을 합친 것.
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
