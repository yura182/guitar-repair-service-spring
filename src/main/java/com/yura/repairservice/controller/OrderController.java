package com.yura.repairservice.controller;

import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.order.Status;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.service.InstrumentService;
import com.yura.repairservice.service.OrderService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class OrderController implements PaginationUtility {
    private final OrderService orderService;

    @GetMapping("/order")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("instrument", new Instrument());
        modelAndView.addObject("order", new Order());
        modelAndView.setViewName("order");

        return modelAndView;
    }

    @PostMapping("/order")
    public ModelAndView saveOrder(@Valid Instrument instrument, BindingResult instrumentResult,
                                  @Valid Order order, BindingResult orderResult) {
        ModelAndView modelAndView = new ModelAndView("order");

        if (instrumentResult.hasErrors() || orderResult.hasErrors()) {
            modelAndView.addObject("orderError", true);
        } else {
            order.setStatus(Status.NEW);
            order.setClient((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            order.setInstrument(instrument);
            order.setDateTime(LocalDateTime.now());

            orderService.add(order);

            modelAndView.addObject("success", true);
        }

        return modelAndView;
    }

    @GetMapping("/user-orders")
    public ModelAndView userOrders(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
        paginationValidate(currentPage, recordsPerPage);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderService
                .findByClient(user.getId(), PageRequest.of(currentPage - 1, recordsPerPage));

        return paginate(currentPage, recordsPerPage, "user-orders",
                orderService.numberOfEntriesByClientId(user.getId()), orders);
    }

    @PostMapping("/order-details")
    public ModelAndView orderDetails(@RequestParam Integer orderId) {
        ModelAndView modelAndView = new ModelAndView("order-details");
        Order order = orderService.findById(orderId);
        modelAndView.addObject("order", order);

        return modelAndView;
    }

    @GetMapping("/all-orders")
    public ModelAndView allOrders(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
        paginationValidate(currentPage, recordsPerPage);

        List<Order> orders = orderService.findAll(PageRequest.of(currentPage - 1, recordsPerPage));

        return paginate(currentPage, recordsPerPage, "all-orders", orderService.numberOfEntries(), orders);
    }


}
