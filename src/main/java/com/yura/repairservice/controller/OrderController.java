package com.yura.repairservice.controller;

import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.service.InstrumentService;
import com.yura.repairservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class OrderController {
    private final InstrumentService instrumentService;
    private final OrderService orderService;

    @GetMapping("/order")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("instrument", new Instrument());
        modelAndView.addObject("order", new Order());
        modelAndView.setViewName("order");

        return modelAndView;
    }

    @PostMapping("/order")
    public ModelAndView saveOrder(@Valid Instrument instrument, BindingResult instrumentResult/*,
                                  @Valid Order order, BindingResult orderResult*/) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(instrument);
        modelAndView.setViewName("order");
        return modelAndView;
    }
}
