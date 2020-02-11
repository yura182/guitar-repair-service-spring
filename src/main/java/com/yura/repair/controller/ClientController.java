package com.yura.repair.controller;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import com.yura.repair.service.OrderService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class ClientController {
    private final OrderService orderService;

    @GetMapping("/client/orders")
    public ModelAndView userOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        UserDto clientDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<OrderDto> orders = orderService.findByClient(clientDto.getId(), pageable);

        ModelAndView modelAndView = new ModelAndView("client-orders");
        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    @GetMapping("/client/order/{orderId}")
    public ModelAndView orderDetails(@PathVariable("orderId") Integer orderId) {
        ModelAndView modelAndView = new ModelAndView();
        OrderDto orderDto = orderService.findById(orderId);
        UserDto loggedUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (orderDto.getClient().getId().equals(loggedUser.getId())) {
            modelAndView.addObject("order", orderDto);
            modelAndView.setViewName("client-order-details");
        } else {
            modelAndView.setViewName("404");
        }

        return modelAndView;
    }

    @GetMapping("/client/add-order")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("instrumentDto", new InstrumentDto());
        modelAndView.addObject("orderDto", new OrderDto());
        modelAndView.setViewName("client-add-order");

        return modelAndView;
    }

    @PostMapping("/client/add-order")
    public ModelAndView saveOrder(@Valid InstrumentDto instrumentDto, BindingResult instrumentResult,
                                  @Valid OrderDto orderDto, BindingResult orderResult,
                                  RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (instrumentResult.hasErrors() || orderResult.hasErrors()) {
            modelAndView.setViewName("client-add-order");
            modelAndView.addObject("errorMessage", "order.error");
        } else {
            orderDto.setStatus(Status.NEW);
            orderDto.setClient((UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            orderDto.setInstrumentDto(instrumentDto);
            orderDto.setDateTime(LocalDateTime.now());

            orderService.add(orderDto);

            modelAndView.setViewName("redirect:/client/add-order");
            redirectAttributes.addFlashAttribute("successMessage", "order.success");
        }

        return modelAndView;
    }
}
