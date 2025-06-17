package com.unmannedstore.config;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Configuration class responsible for initializing sample data in the database.
 * This serves as a backup mechanism in case the data.sql approach doesn't work.
 */
@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Creates a CommandLineRunner bean that checks if products are already loaded
     * and loads them if they're not.
     *
     * @param productRepository The repository for Product entities
     * @return A CommandLineRunner that initializes the database with sample products
     */
    @Bean
    public CommandLineRunner initDatabase(ProductRepository productRepository, ShoppingSessionRepository shoppingSessionRepository) {
        return args -> {
            // Check if products are already loaded
            List<Product> existingProducts = productRepository.findAll();
            if (existingProducts.isEmpty()) {
                logger.info("No products found in database. Initializing with sample data...");

                // Create sample products
                List<Product> sampleProducts = createSampleProducts();

                // Save all products individually
                for (Product product : sampleProducts) {
                    productRepository.save(product);
                }

                logger.info("Sample products initialized successfully. {} products loaded.", sampleProducts.size());
            } else {
                logger.info("Products already exist in database. Skipping initialization.");
                logger.info("Total products in database: {}", existingProducts.size());
            }

            // Check if any active shopping sessions exist
            List<ShoppingSession> activeSessions = shoppingSessionRepository.findAll().stream()
                    .filter(session -> "ACTIVE".equals(session.getStatus()))
                    .toList();

            if (activeSessions.isEmpty()) {
                logger.info("No active shopping sessions found. Creating a sample active session...");

                // Create a sample active shopping session
                ShoppingSession sampleSession = createSampleShoppingSession();

                // Save the session
                sampleSession = shoppingSessionRepository.save(sampleSession);

                // Add a sample item to the basket
                List<Product> products = productRepository.findAll();
                if (!products.isEmpty()) {
                    // Add the first product to the basket
                    Product firstProduct = products.get(0);
                    sampleSession.addItem(firstProduct);

                    // Save the updated session
                    shoppingSessionRepository.save(sampleSession);

                    logger.info("Added product '{}' to the sample shopping session.", firstProduct.getName());
                }

                logger.info("Sample active shopping session created successfully with basket ID: {}", sampleSession.getBasketId());
            } else {
                logger.info("Active shopping sessions already exist. Skipping initialization.");
                logger.info("Total active shopping sessions: {}", activeSessions.size());
            }
        };
    }

    /**
     * Creates a list of sample products.
     * This is a backup for the data.sql approach.
     *
     * @return A list of sample Product entities
     */
    private List<Product> createSampleProducts() {
        // Create sample products (same as in data.sql)
        Product p1 = new Product("p001", "Bottled Water", new BigDecimal("1.99"), "rfid-bw-001");
        p1.setDescription("Refreshing spring water, 500ml bottle");
        p1.setCategory("Beverages");
        p1.setImageUrl("https://example.com/images/bottled-water.jpg");

        Product p2 = new Product("p002", "Cola Drink", new BigDecimal("2.49"), "rfid-cd-002");
        p2.setDescription("Classic cola flavor, 330ml can");
        p2.setCategory("Beverages");
        p2.setImageUrl("https://example.com/images/cola.jpg");

        Product p3 = new Product("p003", "Orange Juice", new BigDecimal("3.99"), "rfid-oj-003");
        p3.setDescription("Fresh squeezed orange juice, 1L carton");
        p3.setCategory("Beverages");
        p3.setImageUrl("https://example.com/images/orange-juice.jpg");

        Product p4 = new Product("p004", "Potato Chips", new BigDecimal("2.99"), "rfid-pc-004");
        p4.setDescription("Crispy salted potato chips, 150g bag");
        p4.setCategory("Snacks");
        p4.setImageUrl("https://example.com/images/potato-chips.jpg");

        Product p5 = new Product("p005", "Chocolate Bar", new BigDecimal("1.79"), "rfid-cb-005");
        p5.setDescription("Milk chocolate bar, 100g");
        p5.setCategory("Snacks");
        p5.setImageUrl("https://example.com/images/chocolate-bar.jpg");

        // Return list of sample products
        return List.of(p1, p2, p3, p4, p5);
    }

    /**
     * Creates a sample shopping session with an active status.
     *
     * @return A sample ShoppingSession entity
     */
    private ShoppingSession createSampleShoppingSession() {
        // Create a sample shopping session
        String customerId = "sample-customer-" + UUID.randomUUID().toString().substring(0, 8);
        String storeId = "store-1";
        String basketId = "basket-" + UUID.randomUUID().toString().substring(0, 8);

        return new ShoppingSession(customerId, storeId, basketId);
    }
}
