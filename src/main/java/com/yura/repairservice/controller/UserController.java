package com.yura.repairservice.controller;

import com.yura.repairservice.domain.user.Role;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController implements PaginationUtility {
    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("login-error")
    public ModelAndView loginError(ModelAndView modelAndView) {
        modelAndView.addObject("loginError", true);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView saveUser(@Valid User user, BindingResult bindingResult,
                                 @RequestParam(name = "passwordConfirmation") String passwordConfirmation) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("registerError", true);
            modelAndView.setViewName("register");
        } else if (!Objects.equals(user.getPassword(), passwordConfirmation)) {
            modelAndView.addObject("confirmationError", true);
            modelAndView.setViewName("register");
        } else {
            user.setRole(Role.CLIENT);
            userService.register(user);
            modelAndView.addObject("justRegistered", true);
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/all-users")
    public ModelAndView allUsers(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
        paginationValidate(currentPage, recordsPerPage);
        List<User> users = userService.findAll(PageRequest.of(currentPage - 1, recordsPerPage));

        return paginate(currentPage, recordsPerPage, "all-users", userService.numberOfEntries(), users);
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");

        return modelAndView;
    }
}
