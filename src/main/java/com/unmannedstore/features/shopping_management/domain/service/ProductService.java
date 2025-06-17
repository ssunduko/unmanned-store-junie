package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for product-related operations.
 */
public interface ProductService {
    
    /**
     * Find a product by its ID.
     * 
     * @param id The product ID
     * @return An Optional containing the product if found, or empty if not found
     */
    Optional<Product> findById(String id);
    
    /**
     * Find a product by its RFID tag.
     * 
     * @param rfidTag The RFID tag
     * @return An Optional containing the product if found, or empty if not found
     */
    Optional<Product> findByRfidTag(String rfidTag);
    
    /**
     * Find all products in a specific category.
     * 
     * @param category The product category
     * @return A list of products in the specified category
     */
    List<Product> findByCategory(String category);
    
    /**
     * Save a product.
     * 
     * @param product The product to save
     * @return The saved product
     */
    Product save(Product product);
    
    /**
     * Delete a product.
     * 
     * @param id The ID of the product to delete
     * @return true if the product was deleted, false if it was not found
     */
    boolean delete(String id);
    
    /**
     * Find all products.
     * 
     * @return A list of all products
     */
    List<Product> findAll();
}