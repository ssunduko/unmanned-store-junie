package com.unmannedstore.config;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for DataInitializer to verify that products are properly loaded during application startup.
 */
public class DataInitializerTest {

    /**
     * Test that products are loaded when the database is empty.
     */
    @Test
    public void testProductsAreLoadedWhenDatabaseIsEmpty() throws Exception {
        // Mock the repositories
        ProductRepository mockProductRepository = Mockito.mock(ProductRepository.class);
        ShoppingSessionRepository mockSessionRepository = Mockito.mock(ShoppingSessionRepository.class);

        // Configure the mocks to return empty lists when findAll() is called
        when(mockProductRepository.findAll()).thenReturn(new ArrayList<>());
        when(mockSessionRepository.findAll()).thenReturn(new ArrayList<>());

        // Configure the save method to return the same object that was passed to it
        when(mockSessionRepository.save(any(ShoppingSession.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mockProductRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create the DataInitializer
        DataInitializer dataInitializer = new DataInitializer();

        // Get the CommandLineRunner bean
        CommandLineRunner runner = dataInitializer.initDatabase(mockProductRepository, mockSessionRepository);

        // Run the CommandLineRunner
        runner.run();

        // Verify that save() was called 4 times (once for each sample product)
        verify(mockProductRepository, times(4)).save(any(Product.class));

        // Verify that a shopping session was created and saved
        verify(mockSessionRepository, times(1)).save(any(ShoppingSession.class));
    }

    /**
     * Test that products are not loaded when the database already has products.
     */
    @Test
    public void testProductsAreNotLoadedWhenDatabaseHasProducts() throws Exception {
        // Mock the repositories
        ProductRepository mockProductRepository = Mockito.mock(ProductRepository.class);
        ShoppingSessionRepository mockSessionRepository = Mockito.mock(ShoppingSessionRepository.class);

        // Create a list with one product
        List<Product> existingProducts = new ArrayList<>();
        existingProducts.add(new Product("existing", "Existing Product", new BigDecimal("9.99"), "rfid-existing"));

        // Create a list with one active session
        List<ShoppingSession> existingSessions = new ArrayList<>();
        ShoppingSession activeSession = new ShoppingSession("customer-1", "store-1", "basket-1");
        existingSessions.add(activeSession);

        // Configure the mocks to return the lists with existing items when findAll() is called
        when(mockProductRepository.findAll()).thenReturn(existingProducts);
        when(mockSessionRepository.findAll()).thenReturn(existingSessions);

        // Create the DataInitializer
        DataInitializer dataInitializer = new DataInitializer();

        // Get the CommandLineRunner bean
        CommandLineRunner runner = dataInitializer.initDatabase(mockProductRepository, mockSessionRepository);

        // Run the CommandLineRunner
        runner.run();

        // Verify that save() was never called for products
        verify(mockProductRepository, never()).save(any(Product.class));

        // Verify that save() was never called for sessions
        verify(mockSessionRepository, never()).save(any(ShoppingSession.class));
    }
}
