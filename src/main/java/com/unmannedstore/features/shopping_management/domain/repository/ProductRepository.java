package com.unmannedstore.features.shopping_management.domain.repository;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity.
 */
public interface ProductRepository {
    
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
     * @param product The product to delete
     */
    void delete(Product product);
    
    /**
     * Find all products.
     * 
     * @return A list of all products
     */
    List<Product> findAll();
}