package com.unmannedstore.features.shopping_management.infrastructure.persistence;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of ProductRepository.
 */
@Repository
public interface JpaProductRepository extends JpaRepository<Product, String>, ProductRepository {
    
    /**
     * Find a product by its RFID tag.
     * 
     * @param rfidTag The RFID tag
     * @return An Optional containing the product if found, or empty if not found
     */
    @Override
    Optional<Product> findByRfidTag(String rfidTag);
    
    /**
     * Find all products in a specific category.
     * 
     * @param category The product category
     * @return A list of products in the specified category
     */
    @Override
    List<Product> findByCategory(String category);
}