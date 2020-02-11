package com.yura.repair.controller;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.entity.Status;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController implements PaginationUtility {
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

    @GetMapping("/user/add-order")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("instrumentDto", new InstrumentDto());
        modelAndView.addObject("orderDto", new OrderDto());
        modelAndView.setViewName("user-add-order");

        return modelAndView;
    }

    @PostMapping("/user/add-order")
    public ModelAndView saveOrder(@Valid InstrumentDto instrumentDto, BindingResult instrumentResult,
                                  @Valid OrderDto orderDto, BindingResult orderResult,
                                  RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (instrumentResult.hasErrors() || orderResult.hasErrors()) {
            modelAndView.setViewName("user-add-order");
            modelAndView.addObject("errorMessage", "order.error");
        } else {
            orderDto.setStatus(Status.NEW);
            orderDto.setClient((UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            orderDto.setInstrumentDto(instrumentDto);
            orderDto.setDateTime(LocalDateTime.now());

            orderService.add(orderDto);

            modelAndView.setViewName("redirect:/user/add-order");
            redirectAttributes.addFlashAttribute("successMessage", "order.success");
        }

        return modelAndView;
    }

    @GetMapping("/user/orders")
    public ModelAndView userOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<OrderDto> orders = orderService.findByClient(userDto.getId(), pageable);

        ModelAndView modelAndView = new ModelAndView("user-orders");
        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    @GetMapping("/user/order/{orderId}")
    public ModelAndView orderDetails(@PathVariable("orderId") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView();
        OrderDto orderDto = orderService.findById(orderId);
        UserDto loggedUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (orderDto.getClient().getId().equals(loggedUser.getId())) {
            modelAndView.addObject("order", orderDto);
            modelAndView.setViewName("user-order-details");
        } else {
            modelAndView.setViewName("404");
        }

        return modelAndView;
    }

//    @GetMapping("/all-orders")
//    public ModelAndView allOrders(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
//        paginationValidate(currentPage, recordsPerPage);
//
//        List<OrderDto> orders = orderService.findAll(PageRequest.of(currentPage - 1, recordsPerPage));
//
//        return paginate(currentPage, recordsPerPage, "all-orders", orderService.numberOfEntries(), orders);
//    }

}
