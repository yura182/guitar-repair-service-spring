package com.yura.repair.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.yura.repair.constant.PageUrl.ERROR_PAGE;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError() {
        return ERROR_PAGE;
    }

    @PostMapping("/error")
    public String handleErrorPost() {
        return ERROR_PAGE;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PAGE;
    }
}
