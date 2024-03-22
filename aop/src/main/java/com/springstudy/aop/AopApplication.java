package com.springstudy.aop;

import com.springstudy.aop.exam.aop.RetryAspect;
import com.springstudy.aop.exam.aop.TraceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({TraceAspect.class, RetryAspect.class})
@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
