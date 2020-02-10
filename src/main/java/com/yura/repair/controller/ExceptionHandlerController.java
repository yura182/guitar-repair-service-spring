package com.yura.repair.controller;

import com.yura.repair.dto.UserDto;
import com.yura.repair.exception.AlreadyRegisteredUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({AlreadyRegisteredUserException.class})
    public ModelAndView handleAlreadyRegistered() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerError", true);
        modelAndView.addObject("alreadyRegistered", true);
        modelAndView.addObject("user", new UserDto());

        return modelAndView;
    }
}
