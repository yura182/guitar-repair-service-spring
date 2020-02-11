package com.yura.repair.controller;

import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, Principal loggedUser) {
        if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Principal loggedUser) {
        if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.addObject("userDto", new UserDto());
            modelAndView.setViewName("register");
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView saveUser(@Valid UserDto userDto, BindingResult bindingResult,
                                 @RequestParam(name = "passwordConfirmation") String passwordConfirmation,
                                 RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errorMessage", "register.error");
            modelAndView.setViewName("register");
        } else if (!Objects.equals(userDto.getPassword(), passwordConfirmation)) {
            modelAndView.addObject("confirmationError", true);
            modelAndView.setViewName("register");
        } else {
            userDto.setRole(Role.CLIENT);
            userService.register(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "login.just.registered");
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "login.error");
        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        modelAndView.addObject("user", userDto);
        modelAndView.setViewName("profile");

        return modelAndView;
    }
}
