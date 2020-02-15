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

import static com.yura.repair.constant.AttributeName.*;
import static com.yura.repair.constant.PageUrl.*;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class MasterController {
    private static final String PROCESS_SUCCESS_MESSAGE = "process.success";
    private static final String PROCESS_ERROR_MESSAGE = "process.error";
    private static final String COMPLETE_SUCCESS_MESSAGE = "complete.success";
    private static final String MASTER_PATH = "/master/";


    private final OrderService orderService;

    @GetMapping(MASTER_PATH + "available-orders")
    public ModelAndView allOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                  ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findByStatus(Status.ACCEPTED, pageable);

        modelAndView.setViewName(MASTER_ALL_ORDERS_PAGE);
        modelAndView.addObject(ATTR_NAME_PAGE, orders);

        return modelAndView;
    }

    @GetMapping(MASTER_PATH + "order/{orderId}")
    public ModelAndView masterOrderDetails(@PathVariable("orderId") Integer orderId,
                                           @AuthenticationPrincipal UserDto loggedMaster,
                                           ModelAndView modelAndView) {

        OrderDto orderDto = orderService.findById(orderId);

        if (orderService.isNotMasterOrder(loggedMaster, orderDto)) {
            modelAndView.setViewName(ERROR_PAGE);

            return modelAndView;
        }

        modelAndView.addObject(ATTR_NAME_ORDER, orderDto);
        modelAndView.setViewName(MASTER_ORDER_DETAILS_PAGE);

        return modelAndView;
    }

    @PostMapping(MASTER_PATH + "process-order")
    public ModelAndView processOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                     @AuthenticationPrincipal UserDto master,
                                     ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        try {
            orderService.processOrder(orderId, master);
            modelAndView.setViewName(REDIRECT + MASTER_ORDER_PATH + orderId);
            redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, PROCESS_SUCCESS_MESSAGE);
        } catch (OrderAlreadyUpdatedException e) {
            log.warn("Order's already processed by another master", e);
            modelAndView.setViewName(REDIRECT + MASTER_AVAILABLE_ORDERS_PATH);
            redirectAttributes.addFlashAttribute(ATTR_NAME_ERROR, PROCESS_ERROR_MESSAGE);
        }

        return modelAndView;
    }

    @PostMapping(MASTER_PATH + "complete-order")
    public ModelAndView completeOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                      ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        orderService.completeOrder(orderId);

        modelAndView.setViewName(REDIRECT + MASTER_ORDER_PATH + orderId);
        redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, COMPLETE_SUCCESS_MESSAGE);

        return modelAndView;
    }

    @GetMapping(MASTER_PATH + "orders")
    public ModelAndView processingOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @AuthenticationPrincipal UserDto loggedMaster, ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findByMaster(loggedMaster.getId(), pageable);

        modelAndView.setViewName(MASTER_ALL_PROCESSING_ORDERS_PAGE);
        modelAndView.addObject(ATTR_NAME_PAGE, orders);

        return modelAndView;
    }


}
