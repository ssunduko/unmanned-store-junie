package com.unmannedstore.features.shopping_management.domain.repository;

import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ShoppingSession entity.
 */
public interface ShoppingSessionRepository {
    
    /**
     * Find a shopping session by its ID.
     * 
     * @param id The shopping session ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    Optional<ShoppingSession> findById(String id);
    
    /**
     * Find active shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of active shopping sessions for the customer
     */
    List<ShoppingSession> findActiveSessionsByCustomerId(String customerId);
    
    /**
     * Find a shopping session by basket ID.
     * 
     * @param basketId The basket ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    Optional<ShoppingSession> findByBasketId(String basketId);
    
    /**
     * Find all shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of all shopping sessions for the customer
     */
    List<ShoppingSession> findByCustomerId(String customerId);
    
    /**
     * Find all shopping sessions for a store.
     * 
     * @param storeId The store ID
     * @return A list of all shopping sessions for the store
     */
    List<ShoppingSession> findByStoreId(String storeId);
    
    /**
     * Save a shopping session.
     * 
     * @param shoppingSession The shopping session to save
     * @return The saved shopping session
     */
    ShoppingSession save(ShoppingSession shoppingSession);
    
    /**
     * Delete a shopping session.
     * 
     * @param shoppingSession The shopping session to delete
     */
    void delete(ShoppingSession shoppingSession);
    
    /**
     * Find all shopping sessions.
     * 
     * @return A list of all shopping sessions
     */
    List<ShoppingSession> findAll();
}