package com.oopinspring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/mvc";
    }

    @GetMapping("/mvc")
    @ResponseBody
    public String home(Locale locale, Model model) {
        return "<h1>show</h1>";
    }
}
