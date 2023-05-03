package hello.servlet.web.springmvc.legacy;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * <스프링이 자동으로 등록하는 HandlerMapping>
 *   0 : RequestMappingHandlerMapping : 어노테이션 기반 @RequestMapping, 어노테이션의 메타 url 정보를 가지고 핸들러 조회
 * ✓ 1 : BeanNameUrlHandlerMapping : 스프링 빈 이름으로 핸들러 조회
 *
 * <스프링이 자동으로 등록하는 HandlerAdapter>
 *   0 : RequestMappingHandlerAdapter : 어노테이션 기반 @RequestMapping
 * ✓ 1 : HttpRequestHandlerAdapter : 서블릿과 유사한 형태
 *   2 : SimpleControllerHandlerAdapter : Controller 인터페이스, v5의 어댑터와 유사(supports, handle)
 */
@Component("/springmvc/request-handler") // url 주소를 bean 이름으로 사용
public class MyHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MyHttpRequestHandler.handleRequest");
    }
}
