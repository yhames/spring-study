package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * View 분리 : view 만을 따로 처리하는 객체 생성
 *  - 컨트롤러에서 뷰 url 주소 반환
 *  - requestDispatcher 를 사용하여 forward 해야하는 중복 제거
 *  - 여전히 모델은 서블릿 사용 -> 서블릿 종속적.
 */
public interface ControllerV2 {

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
