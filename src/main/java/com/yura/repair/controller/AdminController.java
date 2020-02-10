package com.yura.repair.controller;

import com.yura.repair.dto.UserDto;
import com.yura.repair.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView allUsers(Pageable pageable) {
        Page<UserDto> users = userService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("admin-all-users");

        modelAndView.addObject("page", users);

        return modelAndView;
    }
}
