package com.remsoft.orders.service;

import com.remsoft.orders.domain.entity.Order;
import com.remsoft.orders.domain.entity.Product;
import com.remsoft.orders.repository.OrderRepository;
import com.remsoft.orders.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_ReturnsOrderList() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_OrderExists_ReturnsOrder() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_OrderDoesNotExist_ReturnsEmpty() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(1L);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void saveOrder_SuccessfulSave_ReturnsSavedOrder() {
        Order order = new Order();
        Product product = new Product();
        product.setId(1L);
        List<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveOrder(order);

        assertNotNull(savedOrder);
        assertEquals(order, savedOrder);
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void saveOrder_ProductNotFound_ThrowsEntityNotFoundException() {
        Order order = new Order();
        Product product = new Product();
        product.setId(1L);
        List<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.saveOrder(order));

        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(order);
    }

    @Test
    void deleteOrder_OrderExists_DeletesOrder() {
        Long orderId = 1L;

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }
}

