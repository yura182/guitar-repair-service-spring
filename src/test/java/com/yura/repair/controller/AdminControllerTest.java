package com.yura.repair.controller;

import com.yura.repair.dto.OrderDto;
import com.yura.repair.dto.ReviewDto;
import com.yura.repair.dto.UserDto;
import com.yura.repair.service.OrderService;
import com.yura.repair.service.ReviewService;
import com.yura.repair.service.UserService;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AdminController.class)
public class AdminControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserDto userDto;

    @MockBean
    private Pageable pageable;

    @MockBean
    private RedirectAttributes redirectAttributes;

    @MockBean
    private OrderDto orderDto;

    @Autowired
    private AdminController adminController;


    @Test
    public void allUsersShouldReturnUsersPage() {
        Page<UserDto> page = new PageImpl<>(Collections.singletonList(userDto));
        when(userService.findAll(pageable)).thenReturn(page);

        ModelAndView modelAndView = adminController.allUsers(pageable, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(userService).findAll(pageable);
        assertNotNull(model.get(ATTR_NAME_PAGE));
        assertEquals(ADMIN_ALL_USERS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void allOrdersShouldReturnUsersPage() {
        Page<OrderDto> page = new PageImpl<>(Collections.singletonList(orderDto));
        when(orderService.findAll(pageable)).thenReturn(page);

        ModelAndView modelAndView = adminController.allOrders(pageable, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findAll(pageable);
        assertNotNull(model.get(ATTR_NAME_PAGE));
        assertEquals(ADMIN_ALL_ORDERS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void adminOrderDetailsShouldReturnOrderDetailsPage() {
        when(orderService.findById(1)).thenReturn(orderDto);

        ModelAndView modelAndView = adminController.adminOrderDetails(1, new ModelAndView());
        Map<String, Object> model = modelAndView.getModel();

        verify(orderService).findById(1);
        assertNotNull(model.get(ATTR_NAME_ORDER));
        assertEquals(ADMIN_ORDER_DETAILS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void acceptOrderShouldUpdateOrder() {
        ModelAndView modelAndView = adminController.acceptOrder(1.1, 1, new ModelAndView(), redirectAttributes);

        verify(orderService).acceptOrder(1, 1.1);
        assertEquals(REDIRECT + ADMIN_ORDER_PATH + 1, modelAndView.getViewName());
    }

    @Test
    public void rejectOrderShouldUpdateOrder() {
        ModelAndView modelAndView = adminController.rejectOrder(1, "reason", new ModelAndView(), redirectAttributes);

        verify(orderService).rejectOrder(1, "reason");
        assertEquals(REDIRECT + ADMIN_ORDER_PATH + 1, modelAndView.getViewName());
    }

    @Test
    public void deleteReviewShouldDeleteReview() {
        ModelAndView modelAndView = adminController.deleteReview(1, new ModelAndView(), redirectAttributes);

        verify(reviewService).delete(any(ReviewDto.class));
        assertEquals(REDIRECT + REVIEWS_PATH, modelAndView.getViewName());
    }
}