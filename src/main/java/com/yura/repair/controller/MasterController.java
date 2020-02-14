package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import com.yura.repair.exception.OrderAlreadyUpdatedException;
import com.yura.repair.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class MasterController {
    private final OrderService orderService;

    @GetMapping("/master/available-orders")
    public ModelAndView allOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                  ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findByStatus(Status.ACCEPTED, pageable);

        modelAndView.setViewName("master-all-orders");
        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    @GetMapping("/master/order/{orderId}")
    public ModelAndView masterOrderDetails(@PathVariable("orderId") Integer orderId,
                                           @AuthenticationPrincipal UserDto loggedMaster,
                                           ModelAndView modelAndView) {

        OrderDto orderDto = orderService.findById(orderId);

        if (checkAccessToOrder(loggedMaster, orderDto)) {
            modelAndView.setViewName("404");

            return modelAndView;
        }

        modelAndView.addObject("order", orderDto);
        modelAndView.setViewName("master-order-details");

        return modelAndView;
    }

    @PostMapping("/master/process-order")
    public ModelAndView processOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                     @AuthenticationPrincipal UserDto master,
                                     ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        try {
            orderService.processOrder(orderId, master);
            modelAndView.setViewName("redirect:/master/order/" + orderId);
            redirectAttributes.addFlashAttribute("successMessage", "process.success");
        } catch (OrderAlreadyUpdatedException e) {
            log.warn("Order's already processed by another master", e);
            modelAndView.setViewName("redirect:/master/available-orders");
            redirectAttributes.addFlashAttribute("errorMessage", "process.error");
        }

        return modelAndView;
    }

    @PostMapping("/master/complete-order")
    public ModelAndView completeOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                      ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        orderService.completeOrder(orderId);

        modelAndView.setViewName("redirect:/master/order/" + orderId);
        redirectAttributes.addFlashAttribute("successMessage", "complete.success");

        return modelAndView;
    }

    @GetMapping("/master/orders")
    public ModelAndView processingOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @AuthenticationPrincipal UserDto loggedMaster, ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findByMaster(loggedMaster.getId(), pageable);

        modelAndView.setViewName("master-all-processing-orders");
        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    private boolean checkAccessToOrder(UserDto loggedMaster, OrderDto orderDto) {
        return Objects.nonNull(orderDto.getMaster()) && !orderDto.getMaster().getId().equals(loggedMaster.getId());
    }
}
