package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Status;
import com.yura.repair.exception.OrderAlreadyUpdatedException;
import com.yura.repair.service.OrderService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;

import static com.yura.repair.constant.AttributeName.ATTR_NAME_ORDER;
import static com.yura.repair.constant.AttributeName.ATTR_NAME_PAGE;
import static com.yura.repair.constant.PageUrl.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MasterController.class)
public class MasterControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private RedirectAttributes redirectAttributes;

    @MockBean
    private UserDto userDto;

    @MockBean
    private OrderDto orderDto;

    @MockBean
    private Pageable pageable;

    @Autowired
    private MasterController masterController;

    @After
    public void resetMocks() {
        reset(orderService, pageable, userDto, orderDto, redirectAttributes);
    }

    @Test
    public void allOrdersShouldReturnOrders() {
        Page<OrderDto> page = new PageImpl<>(Collections.singletonList(orderDto));
        when(orderService.findByStatus(any(Status.class), any(Pageable.class))).thenReturn(page);

        ModelAndView modelAndView = masterController.allOrders(pageable, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findByStatus(Status.ACCEPTED, pageable);
        assertNotNull(model.get(ATTR_NAME_PAGE));
        assertEquals(MASTER_ALL_ORDERS_PAGE, modelAndView.getViewName());

    }

    @Test
    public void masterOrderDetailShouldShowOrderPage() {
        when(orderService.findById(1)).thenReturn(orderDto);
        when(orderService.isNotMasterOrder(userDto, orderDto)).thenReturn(false);

        ModelAndView modelAndView = masterController.masterOrderDetails(1, userDto, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findById(1);
        verify(orderService).isNotMasterOrder(userDto, orderDto);
        assertNotNull(model.get(ATTR_NAME_ORDER));
        assertEquals(MASTER_ORDER_DETAILS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void masterOrderDetailShouldShowErrorPage() {
        when(orderService.findById(1)).thenReturn(orderDto);
        when(orderService.isNotMasterOrder(userDto, orderDto)).thenReturn(true);

        ModelAndView modelAndView = masterController.masterOrderDetails(1, userDto, new ModelAndView());

        verify(orderService).findById(1);
        verify(orderService).isNotMasterOrder(userDto, orderDto);
        assertEquals(ERROR_PAGE, modelAndView.getViewName());
    }

    @Test
    public void processOrderShouldUpdateOrder() {
        doNothing().when(orderService).processOrder(1, userDto);

        ModelAndView modelAndView = masterController.processOrder(1, userDto, new ModelAndView(), redirectAttributes);

        verify(orderService).processOrder(1, userDto);
        assertEquals(REDIRECT + MASTER_ORDER_PATH + 1, modelAndView.getViewName());
    }

    @Test
    public void processOrderShouldNotUpdateOrder() {
        doThrow(OrderAlreadyUpdatedException.class).when(orderService).processOrder(1, userDto);
        ModelAndView modelAndView = masterController.processOrder(1, userDto, new ModelAndView(), redirectAttributes);

        verify(orderService).processOrder(1, userDto);
        assertEquals(REDIRECT + MASTER_AVAILABLE_ORDERS_PATH, modelAndView.getViewName());
    }

    @Test
    public void completeOrderShouldUpdateOrder() {
        doNothing().when(orderService).completeOrder(1);

        ModelAndView modelAndView = masterController.completeOrder(1, new ModelAndView(), redirectAttributes);

        verify(orderService).completeOrder(1);
        assertEquals(REDIRECT + MASTER_ORDER_PATH + 1, modelAndView.getViewName());
    }

    @Test
    public void processingOrdersShouldReturnListOfOrders() {
        Page<OrderDto> page = new PageImpl<>(Collections.singletonList(orderDto));
        when(orderService.findByMaster(userDto.getId(), pageable)).thenReturn(page);

        ModelAndView modelAndView = masterController.processingOrders(pageable, userDto, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findByMaster(userDto.getId(), pageable);
        assertNotNull(model.get(ATTR_NAME_PAGE));
        assertEquals(MASTER_ALL_PROCESSING_ORDERS_PAGE, modelAndView.getViewName());
    }
}