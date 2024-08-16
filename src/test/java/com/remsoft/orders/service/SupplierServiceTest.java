package com.remsoft.orders.service;

import com.remsoft.orders.domain.entity.Supplier;
import com.remsoft.orders.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSuppliers() {
        Supplier supplier1 = Supplier.builder().id(1L).supplierName("Supplier 1").contact("Contact 1").build();
        Supplier supplier2 = Supplier.builder().id(2L).supplierName("Supplier 2").contact("Contact 2").build();
        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);

        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<Supplier> result = supplierService.getAllSuppliers();

        assertEquals(2, result.size());
        assertEquals("Supplier 1", result.get(0).getSupplierName());
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void testSaveSupplier() {
        Supplier supplier = Supplier.builder().id(1L).supplierName("New Supplier").contact("New Contact").build();

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.saveSupplier(supplier);

        assertEquals("New Supplier", result.getSupplierName());
        verify(supplierRepository, times(1)).save(supplier);
    }
}
