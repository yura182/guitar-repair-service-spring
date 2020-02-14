package com.yura.repair.controller;

import com.yura.repair.dto.UserDto;
import com.yura.repair.exception.AlreadyRegisteredUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static com.yura.repair.constant.AttributeName.ATTR_NAME_ERROR;
import static com.yura.repair.constant.AttributeName.ATTR_NAME_USER;
import static com.yura.repair.constant.PageUrl.ERROR_PAGE;
import static com.yura.repair.constant.PageUrl.REGISTER_PAGE;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final String ALREADY_REGISTERED_MESSAGE = "register.error.already.registered";

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException() {
        return ERROR_PAGE;
    }

    @ExceptionHandler(AlreadyRegisteredUserException.class)
    public ModelAndView handleAlreadyRegistered() {
        ModelAndView modelAndView = new ModelAndView(REGISTER_PAGE);

        modelAndView.addObject(ATTR_NAME_ERROR, ALREADY_REGISTERED_MESSAGE);
        modelAndView.addObject(ATTR_NAME_USER, new UserDto());

        return modelAndView;
    }
}
