package com.yura.repair.controller;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.ReviewService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class ClientController {
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping("/client/orders")
    public ModelAndView userOrders(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                   @AuthenticationPrincipal UserDto clientDto, ModelAndView modelAndView) {

        Page<OrderDto> orders = orderService.findByClient(clientDto.getId(), pageable);

        modelAndView.setViewName("client-orders");
        modelAndView.addObject("page", orders);

        return modelAndView;
    }

    @GetMapping("/client/order/{orderId}")
    public ModelAndView orderDetails(@PathVariable("orderId") Integer orderId,
                                     @AuthenticationPrincipal UserDto loggedUser, ModelAndView modelAndView) {

        OrderDto orderDto = orderService.findById(orderId);

        if (!orderDto.getClient().getId().equals(loggedUser.getId())) {
            modelAndView.setViewName("404");
        }

        modelAndView.addObject("order", orderDto);
        modelAndView.setViewName("client-order-details");

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
                                  @AuthenticationPrincipal UserDto client,
                                  ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        if (instrumentResult.hasErrors() || orderResult.hasErrors()) {
            modelAndView.setViewName("client-add-order");
            modelAndView.addObject("errorMessage", "order.error");
            return modelAndView;
        }

        orderDto.setStatus(Status.NEW);
        orderDto.setClient(client);
        orderDto.setInstrumentDto(instrumentDto);
        orderDto.setDateTime(LocalDateTime.now());

        orderService.add(orderDto);

        modelAndView.setViewName("redirect:/client/add-order");
        redirectAttributes.addFlashAttribute("successMessage", "order.success");

        return modelAndView;
    }

    @PostMapping("client/leave-review")
    public ModelAndView leaveReview(@RequestParam(name = "orderId") @NotNull Integer orderId,
                                    @RequestParam(name = "text") @NotBlank String text,
                                    @AuthenticationPrincipal UserDto client,
                                    ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

        ReviewDto review = ReviewDto.builder()
                .client(client)
                .orderDto(OrderDto.builder().id(orderId).build())
                .date(LocalDateTime.now())
                .text(text)
                .build();

        reviewService.add(review);

        modelAndView.setViewName("redirect:/client/order/" + orderId);
        redirectAttributes.addFlashAttribute("successMessage", "user.order.details.review.success");

        return modelAndView;
    }
}
