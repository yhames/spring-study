package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ModelView {
    private String viewName;    // 뷰 논리 이름
    private Map<String, Object> model = new HashMap<>();    // 모델 객체

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
