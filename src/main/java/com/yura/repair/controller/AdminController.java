package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/admin/users")
    public ModelAndView allUsers(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserDto> users = userService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("admin-all-users");

        modelAndView.addObject("page", users);

        return modelAndView;
    }

    @GetMapping("/admin/orders")
    public ModelAndView allOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderDto> orders = orderService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("admin-all-orders");

        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    @GetMapping("/admin/order/{orderId}")
    public ModelAndView adminOrderDetails(@PathVariable("orderId") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView();
        OrderDto orderDto = orderService.findById(orderId);

        modelAndView.addObject("order", orderDto);
        modelAndView.setViewName("admin-order-details");

        return modelAndView;
    }

    @PostMapping("/admin/accept-order")
    public ModelAndView acceptOrder(@RequestParam(name = "price") @NotNull Double price,
                                    @RequestParam(name = "orderId") @NotNull Integer orderId,
                                    RedirectAttributes redirectAttributes) {

        orderService.acceptOrder(orderId, price);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/order/" + orderId);
        redirectAttributes.addFlashAttribute("successMessage", "accept.success");

        return modelAndView;
    }

    @PostMapping("/admin/reject-order")
    public ModelAndView acceptOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                    @RequestParam(name = "rejectionReason") @NotBlank String rejectionReason,
                                    RedirectAttributes redirectAttributes) {

        orderService.rejectOrder(orderId, rejectionReason);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/order/" + orderId);
        redirectAttributes.addFlashAttribute("successMessage", "reject.success");

        return modelAndView;
    }
}
