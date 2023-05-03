package com.example.core.web;

import com.example.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;    // Proxy

    public void logic(String id) {
        myLogger.log("service id :" + id);
    }
}
