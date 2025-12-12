package com.hw.hwjobbackend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestPageController {
    @GetMapping("/test-chat")
    public String testChat() {
        return "test-chat";
    }
}
