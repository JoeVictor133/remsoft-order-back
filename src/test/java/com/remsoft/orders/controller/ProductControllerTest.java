package com.remsoft.orders.controller;

import com.remsoft.orders.domain.entity.Product;
import com.remsoft.orders.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductName("Product 2");

        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("New Product");

        when(productService.saveProduct(product)).thenReturn(product);

        Product result = productController.createProduct(product);

        assertNotNull(result);
        assertEquals("New Product", result.getProductName());
        verify(productService, times(1)).saveProduct(product);
    }
}

