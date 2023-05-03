package hello.mvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    /**
     * ModelAndView 반환
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;
    }

    //    @ResponseBody   // 있으면 HttpMessageConverter 실행, 없으면 viewResolver 실행
    /**
     * String 반환, Model 사용
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    @RequestMapping("/response/hello")  // 명시성이 떨어지고 딱 맞는 경우도 없어서 권장하지 않음.
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
