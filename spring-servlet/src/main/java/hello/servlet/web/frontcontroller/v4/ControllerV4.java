package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

/**
 * ModelView 직접 사용하지 않음
 * 모델을 매개변수로 넘겨준다. -> 컨트롤러에서 매번 모델뷰 생성해서 값 넣는 중복작업 제거
 * 컨트롤러는 뷰 이름만을 반환하여 리졸버에서 url 주소로 변환.
 */
public interface ControllerV4 {

    /**
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
