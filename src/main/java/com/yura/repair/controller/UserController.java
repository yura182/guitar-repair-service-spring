package com.yura.repair.controller;

import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.service.ReviewService;
import com.yura.repair.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

import static com.yura.repair.constant.AttributeName.*;
import static com.yura.repair.constant.PageUrl.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {
    public static final String MESSAGE_REGISTER_ERROR = "register.error";
    public static final String MESSAGE_JUST_REGISTERED = "login.just.registered";
    public static final String MESSAGE_LOGIN_ERROR = "login.error";

    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String main() {
        return INDEX_PAGE;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, Principal loggedUser) {
        if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName(REDIRECT_HOME_PAGE);
        } else {
            modelAndView.setViewName(LOGIN_PAGE);
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Principal loggedUser) {
        if (Objects.nonNull(loggedUser)) {
            modelAndView.setViewName(REDIRECT_HOME_PAGE);
        } else {
            modelAndView.addObject(ATTR_NAME_USER, new UserDto());
            modelAndView.setViewName(REGISTER_PAGE);
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView saveUser(@Valid UserDto userDto, BindingResult bindingResult,
                                 @RequestParam(name = "passwordConfirmation") String passwordConfirmation,
                                 ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(ATTR_NAME_ERROR, MESSAGE_REGISTER_ERROR);
            modelAndView.setViewName(REGISTER_PAGE);

            return modelAndView;
        }

        if (!Objects.equals(userDto.getPassword(), passwordConfirmation)) {
            modelAndView.addObject(ATTR_NAME_CONFIRMATION_ERROR, true);
            modelAndView.setViewName(REGISTER_PAGE);

            return modelAndView;
        }

        userDto.setRole(Role.CLIENT);
        userService.register(userDto);

        redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, MESSAGE_JUST_REGISTERED);
        modelAndView.setViewName(REDIRECT + LOGIN_PAGE);

        return modelAndView;
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ATTR_NAME_ERROR, MESSAGE_LOGIN_ERROR);
        modelAndView.setViewName(REDIRECT + LOGIN_PAGE);

        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal UserDto userDto, ModelAndView modelAndView) {
        modelAndView.addObject(ATTR_NAME_USER, userDto);
        modelAndView.setViewName(PROFILE_PAGE);

        return modelAndView;
    }

    @GetMapping("/reviews")
    public ModelAndView reviews(@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                ModelAndView modelAndView) {

        Page<ReviewDto> reviews = reviewService.findAll(pageable);
        modelAndView.setViewName(REVIEWS_PAGE);
        modelAndView.addObject(ATTR_NAME_PAGE, reviews);

        return modelAndView;
    }
}
