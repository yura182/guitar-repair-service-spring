package com.yura.repair.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError() {
        return "404";
    }

    @PostMapping("/error")
    public String handleErrorPost() {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "404";
    }
}
