package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/**
 * Model 분리 from Servlet
 * 서블릿 종속성 제거 : 컨트롤러에서 서블릿 속성값이 아닌 새로운 모델 객체(ModelView)를 반환.
 * 뷰 이름 중복 제거 : 컨트롤러에서 뷰 논리 이름 반환. 뷰 논리 이름은 뷰 리졸버에서 url 주소로 변환
 * 하지만, 컨트롤러에서 모델뷰를 반복해서 생성해줘야하는 단점이 있음.
 */
public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);    // 서블릿 종속성 없음
}
