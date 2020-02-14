package com.yura.repair.controller;

import com.yura.repair.dto.UserDto;
import com.yura.repair.exception.AlreadyRegisteredUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException() {
        return "404";
    }

    @ExceptionHandler(AlreadyRegisteredUserException.class)
    public ModelAndView handleAlreadyRegistered() {
        ModelAndView modelAndView = new ModelAndView("register");
        
        modelAndView.addObject("errorMessage", "register.error.already.registered");
        modelAndView.addObject("userDto", new UserDto());

        return modelAndView;
    }
}
