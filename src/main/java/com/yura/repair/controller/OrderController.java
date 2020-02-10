package com.yura.repair.controller;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.dto.UserDto;
import com.yura.repair.service.OrderService;
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
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class OrderController implements PaginationUtility {
    private final OrderService orderService;

    @GetMapping("/order")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject("instrument", new InstrumentDto());
        modelAndView.addObject("order", new OrderDto());
        modelAndView.setViewName("order");

        return modelAndView;
    }

    @PostMapping("/order")
    public ModelAndView saveOrder(@Valid InstrumentDto instrumentDto, BindingResult instrumentResult,
                                  @Valid OrderDto orderDto, BindingResult orderResult) {
        ModelAndView modelAndView = new ModelAndView("order");

        if (instrumentResult.hasErrors() || orderResult.hasErrors()) {
            modelAndView.addObject("orderError", true);
        } else {
            orderDto.setStatus(Status.NEW);
            orderDto.setClient((UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            orderDto.setInstrumentDto(instrumentDto);
            orderDto.setDateTime(LocalDateTime.now());

            orderService.add(orderDto);

            modelAndView.addObject("success", true);
        }

        return modelAndView;
    }

    @GetMapping("/user-orders")
    public ModelAndView userOrders(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
        paginationValidate(currentPage, recordsPerPage);

        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDto> orders = orderService
                .findByClient(userDto.getId(), PageRequest.of(currentPage - 1, recordsPerPage));

        return paginate(currentPage, recordsPerPage, "user-orders",
                orderService.numberOfEntriesByClientId(userDto.getId()), orders);
    }

    @PostMapping("/order-details")
    public ModelAndView orderDetails(@RequestParam Integer orderId) {
        ModelAndView modelAndView = new ModelAndView("order-details");
        OrderDto orderDto = orderService.findById(orderId);
        modelAndView.addObject("order", orderDto);

        return modelAndView;
    }

    @GetMapping("/all-orders")
    public ModelAndView allOrders(@RequestParam Integer currentPage, @RequestParam Integer recordsPerPage) {
        paginationValidate(currentPage, recordsPerPage);

        List<OrderDto> orders = orderService.findAll(PageRequest.of(currentPage - 1, recordsPerPage));

        return paginate(currentPage, recordsPerPage, "all-orders", orderService.numberOfEntries(), orders);
    }


}
