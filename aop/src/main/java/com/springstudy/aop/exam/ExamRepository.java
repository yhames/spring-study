package com.springstudy.aop.exam;

import com.springstudy.aop.exam.annotation.Retry;
import com.springstudy.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    @Trace
    @Retry(value = 4)
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
