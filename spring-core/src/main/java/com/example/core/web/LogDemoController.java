package com.example.core.web;

import com.example.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;    // Proxy

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestUrl(requestURL); // 컨트롤러보다는 공통처리가 가능한 인터셉터가 필터에서 처리하는게 좋음

        myLogger.log("Controller Test");
        logDemoService.logic("testID");
        return "OK";
    }
}
