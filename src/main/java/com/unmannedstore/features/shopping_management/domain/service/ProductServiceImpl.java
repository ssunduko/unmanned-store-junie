package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProductService.
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    /**
     * Constructor for ProductServiceImpl.
     * 
     * @param productRepository The product repository
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Find a product by its ID.
     * 
     * @param id The product ID
     * @return An Optional containing the product if found, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }
    
    /**
     * Find a product by its RFID tag.
     * 
     * @param rfidTag The RFID tag
     * @return An Optional containing the product if found, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByRfidTag(String rfidTag) {
        return productRepository.findByRfidTag(rfidTag);
    }
    
    /**
     * Find all products in a specific category.
     * 
     * @param category The product category
     * @return A list of products in the specified category
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    /**
     * Save a product.
     * 
     * @param product The product to save
     * @return The saved product
     */
    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Delete a product.
     * 
     * @param id The ID of the product to delete
     * @return true if the product was deleted, false if it was not found
     */
    @Override
    @Transactional
    public boolean delete(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }
    
    /**
     * Find all products.
     * 
     * @return A list of all products
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}