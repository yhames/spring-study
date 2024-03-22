package com.springstudy.aop.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public String save(String itemId) {
        return examRepository.save(itemId);
    }
}
