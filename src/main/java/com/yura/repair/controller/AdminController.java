package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.ReviewService;
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

import static com.yura.repair.constant.AttributeName.*;
import static com.yura.repair.constant.PageUrl.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AdminController {
    private static final String ACCEPT_SUCCESS_MESSAGE = "accept.success";
    private static final String REJECT_SUCCESS_MESSAGE = "reject.success";
    private static final String DELETE_SUCCESS_MESSAGE = "all.reviews.delete.success";

    private final UserService userService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping("/admin/users")
    public ModelAndView allUsers(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 ModelAndView modelAndView) {

        Page<UserDto> users = userService.findAll(pageable);

        modelAndView.setViewName(ADMIN_ALL_USERS_PAGE);
        modelAndView.addObject(ATTR_NAME_PAGE, users);

        return modelAndView;
    }

    @GetMapping("/admin/orders")
    public ModelAndView allOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                  ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findAll(pageable);

        modelAndView.setViewName(ADMIN_ALL_ORDERS_PAGE);
        modelAndView.addObject(ATTR_NAME_PAGE, orders);

        return modelAndView;

    }

    @GetMapping("/admin/order/{orderId}")
    public ModelAndView adminOrderDetails(@PathVariable("orderId") Integer orderId, ModelAndView modelAndView) {
        OrderDto orderDto = orderService.findById(orderId);

        modelAndView.setViewName(ADMIN_ORDER_DETAILS_PAGE);
        modelAndView.addObject(ATTR_NAME_ORDER, orderDto);

        return modelAndView;
    }

    @PostMapping("/admin/accept-order")
    public ModelAndView acceptOrder(@RequestParam(name = "price") @NotNull Double price,
                                    @RequestParam(name = "orderId") @NotNull Integer orderId,
                                    ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        orderService.acceptOrder(orderId, price);

        modelAndView.setViewName(REDIRECT + ADMIN_ORDER_PATH + orderId);
        redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, ACCEPT_SUCCESS_MESSAGE);

        return modelAndView;
    }


    @PostMapping("/admin/reject-order")
    public ModelAndView acceptOrder(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                    @RequestParam(name = "rejectionReason") @NotBlank String rejectionReason,
                                    ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        orderService.rejectOrder(orderId, rejectionReason);

        modelAndView.setViewName(REDIRECT + ADMIN_ORDER_PATH + orderId);
        redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, REJECT_SUCCESS_MESSAGE);

        return modelAndView;
    }

    @PostMapping("admin/delete-review")
    public ModelAndView deleteReview(@RequestParam(name = "reviewId") @NotNull Integer reviewId,
                                     ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        reviewService.delete(ReviewDto.builder().id(reviewId).build());

        modelAndView.setViewName(REDIRECT + REVIEWS_PATH);
        redirectAttributes.addFlashAttribute(ATTR_NAME_SUCCESS, DELETE_SUCCESS_MESSAGE);

        return modelAndView;
    }
}
