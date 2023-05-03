package hello.servlet.web.springmvc.legacy;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 핸들러 매핑 : 스프링 빈 이름으로 핸들러 조회
 * 핸들러 어댑터 : Controller 인터페이스를 실행할 수 있는 핸들러 어댑터 조회
 *
 * <스프링이 자동으로 등록하는 HandlerMapping>
 *   0 : RequestMappingHandlerMapping : 어노테이션 기반 @RequestMapping
 * ✓ 1 : BeanNameUrlHandlerMapping : 스프링 빈 이름으로 핸들러 조회
 *
 * <스프링이 자동으로 등록하는 HandlerAdapter>
 *   0 : RequestMappingHandlerAdapter : 어노테이션 기반 @RequestMapping
 *   1 : HttpRequestHandlerAdapter : 서블릿과 유사한 형태
 * ✓ 2 : SimpleControllerHandlerAdapter : Controller 인터페이스, v5의 어댑터와 유사(supports, handle)
 */
@Component("/springmvc/old-controller") // url 주소를 bean 이름으로 사용
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        /**
         * <뷰 리졸버>
         * InternalResourceViewResolver(String prefix, String suffix) : 내부 자원 이동
         * application.properties 파일에 등록한 spring.mvc.view.prefix/suffix 정보를 사용하여 view resolve
         * InternalResourceView() : jsp 처림 forward()를 통해서 호출 가능한 경우에 사용
         * 참고 : thymeleaf 사용시 ThymeleafViewResolver 등록, 스프링부트에서 자동으로 등록해줌
         */
        return new ModelAndView("new-form");    // 논리 이름 반환
    }
}
