package com.remsoft.orders.controller;

import com.remsoft.orders.domain.entity.Supplier;
import com.remsoft.orders.service.SupplierService;
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

class SupplierControllerTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSuppliers() {
        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);
        supplier1.setSupplierName("Supplier 1");

        Supplier supplier2 = new Supplier();
        supplier2.setId(2L);
        supplier2.setSupplierName("Supplier 2");

        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);

        when(supplierService.getAllSuppliers()).thenReturn(suppliers);

        List<Supplier> result = supplierController.getAllSuppliers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplierService, times(1)).getAllSuppliers();
    }

    @Test
    void testCreateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setSupplierName("New Supplier");

        when(supplierService.saveSupplier(supplier)).thenReturn(supplier);

        Supplier result = supplierController.createSupplier(supplier);

        assertNotNull(result);
        assertEquals("New Supplier", result.getSupplierName());
        verify(supplierService, times(1)).saveSupplier(supplier);
    }
}

