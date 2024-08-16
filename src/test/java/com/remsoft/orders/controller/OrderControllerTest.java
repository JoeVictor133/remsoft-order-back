package com.remsoft.orders.controller;

import com.remsoft.orders.domain.entity.Order;
import com.remsoft.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_shouldReturnListOfOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderService.getAllOrders()).thenReturn(orders);

        List<Order> result = orderController.getAllOrders();

        assertEquals(2, result.size());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_shouldReturnOrderWhenFound() {
        Long orderId = 1L;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void getOrderById_shouldReturnNotFoundWhenOrderNotFound() {
        Long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        Order order = new Order();
        when(orderService.saveOrder(order)).thenReturn(order);

        Order result = orderController.createOrder(order);

        assertEquals(order, result);
        verify(orderService, times(1)).saveOrder(order);
    }

    @Test
    void deleteOrder_shouldDeleteOrder() {
        Long orderId = 1L;
        doNothing().when(orderService).deleteOrder(orderId);

        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(orderId);
    }
}
