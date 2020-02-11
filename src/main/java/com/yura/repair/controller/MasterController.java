package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class MasterController {
    private final OrderService orderService;

    @GetMapping("/master/orders")
    public ModelAndView allOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderDto> orders = orderService.findByStatus(Status.ACCEPTED, pageable);
        ModelAndView modelAndView = new ModelAndView("master-all-orders");

        modelAndView.addObject("page", orders);

        return modelAndView;
    }
}
