package com.remsoft.orders.service;

import com.remsoft.orders.domain.entity.Order;
import com.remsoft.orders.domain.entity.Product;
import com.remsoft.orders.repository.OrderRepository;
import com.remsoft.orders.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order saveOrder(Order order) {
        List<Product> managedProducts = new ArrayList<>();
        for (Product product : order.getProducts()) {
            Product managedProduct = productRepository.findById(product.getId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            managedProduct.setOrder(order);
            managedProducts.add(managedProduct);
        }
        order.setProducts(managedProducts);
        return orderRepository.save(order);
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
