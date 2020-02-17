package com.yura.repair.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.yura.repair.constant.PageUrl.ERROR_PAGE;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public String handleError() {
        return ERROR_PAGE;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PAGE;
    }
}
