package com.yura.repair.controller;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.ReviewService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;

import static com.yura.repair.constant.AttributeName.*;
import static com.yura.repair.constant.PageUrl.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ClientController.class)
public class ClientControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private RedirectAttributes redirectAttributes;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    private UserDto userDto;

    @MockBean
    private OrderDto orderDto;

    @MockBean
    private InstrumentDto instrumentDto;

    @MockBean
    private Pageable pageable;

    @Autowired
    private ClientController clientController;

    @After
    public void resetMocks() {
        reset(orderService, pageable, userDto, orderDto, redirectAttributes, reviewService);
    }

    @Test
    public void userOrdersShouldReturnOrders() {
        Page<OrderDto> page = new PageImpl<>(Collections.singletonList(orderDto));
        when(orderService.findByClient(userDto.getId(), pageable)).thenReturn(page);

        ModelAndView modelAndView = clientController.userOrders(pageable, userDto, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findByClient(userDto.getId(), pageable);
        assertNotNull(model.get(ATTR_NAME_PAGE));
        assertEquals(CLIENT_ORDERS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void orderDetailsShouldShowOrderPage() {
        when(orderService.findById(orderDto.getId())).thenReturn(orderDto);
        when(orderService.isNotUserOrder(userDto, orderDto)).thenReturn(false);

        ModelAndView modelAndView = clientController.orderDetails(orderDto.getId(), userDto, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findById(orderDto.getId());
        verify(orderService).isNotUserOrder(userDto, orderDto);
        assertNotNull(model.get(ATTR_NAME_ORDER));
        assertEquals(CLIENT_ORDER_DETAILS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void orderDetailsShouldShowErrorPage() {
        when(orderService.findById(orderDto.getId())).thenReturn(orderDto);
        when(orderService.isNotUserOrder(userDto, orderDto)).thenReturn(true);

        ModelAndView modelAndView = clientController.orderDetails(orderDto.getId(), userDto, new ModelAndView());

        verify(orderService).findById(orderDto.getId());
        verify(orderService).isNotUserOrder(userDto, orderDto);
        assertEquals(ERROR_PAGE, modelAndView.getViewName());
    }

    @Test
    public void saveOrderShouldShowAddOrderPage() {
        ModelAndView modelAndView = clientController.saveOrder(new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        assertNotNull(model.get(ATTR_NAME_INSTRUMENT));
        assertNotNull(model.get(ATTR_NAME_ORDER));
        assertEquals(CLIENT_ADD_ORDER_PAGE, modelAndView.getViewName());
    }

    @Test
    public void saveOrderShouldSaveOrder() {
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView modelAndView = clientController.saveOrder(instrumentDto,
                bindingResult, orderDto, bindingResult, userDto, new ModelAndView(), redirectAttributes);

        verify(orderService).add(orderDto);
        assertEquals(REDIRECT + CLIENT_ADD_ORDER_PATH, modelAndView.getViewName());
    }

    @Test
    public void saveOrderShouldNotSaveOrder() {
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView modelAndView = clientController.saveOrder(instrumentDto,
                bindingResult, orderDto, bindingResult, userDto, new ModelAndView(), redirectAttributes);
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService, times(0)).add(orderDto);
        assertNotNull(model.get(ATTR_NAME_ERROR));
        assertEquals(CLIENT_ADD_ORDER_PAGE, modelAndView.getViewName());
    }

    @Test
    public void leaveReviewShouldSaveReview() {
        ModelAndView modelAndView = clientController.leaveReview(1, "text", userDto, new ModelAndView(), redirectAttributes);

        verify(reviewService).add(any(ReviewDto.class));
        assertEquals(REDIRECT + CLIENT_ORDER_PATH + 1, modelAndView.getViewName());
    }
}