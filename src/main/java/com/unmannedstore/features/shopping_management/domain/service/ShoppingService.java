package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for shopping-related operations.
 */
public interface ShoppingService {
    
    /**
     * Create a new shopping session.
     * 
     * @param customerId The ID of the customer
     * @param storeId The ID of the store
     * @param basketId The ID of the basket assigned to the customer
     * @return The created shopping session
     */
    ShoppingSession createSession(String customerId, String storeId, String basketId);
    
    /**
     * Find a shopping session by its ID.
     * 
     * @param sessionId The shopping session ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    Optional<ShoppingSession> findSessionById(String sessionId);
    
    /**
     * Find a shopping session by basket ID.
     * 
     * @param basketId The basket ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    Optional<ShoppingSession> findSessionByBasketId(String basketId);
    
    /**
     * Add an item to a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @param productId The product ID
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or product is not found
     */
    ShoppingSession addItemToSession(String sessionId, String productId);
    
    /**
     * Add an item to a shopping session by RFID tag.
     * 
     * @param sessionId The shopping session ID
     * @param rfidTag The RFID tag of the product
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or product is not found
     */
    ShoppingSession addItemToSessionByRfidTag(String sessionId, String rfidTag);
    
    /**
     * Remove an item from a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @param itemId The basket item ID
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or item is not found
     */
    ShoppingSession removeItemFromSession(String sessionId, String itemId);
    
    /**
     * Get all items in a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return A list of basket items
     * @throws IllegalArgumentException if the session is not found
     */
    List<BasketItem> getSessionItems(String sessionId);
    
    /**
     * Complete a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return The completed shopping session
     * @throws IllegalArgumentException if the session is not found
     */
    ShoppingSession completeSession(String sessionId);
    
    /**
     * Cancel a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return The cancelled shopping session
     * @throws IllegalArgumentException if the session is not found
     */
    ShoppingSession cancelSession(String sessionId);
    
    /**
     * Find active shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of active shopping sessions for the customer
     */
    List<ShoppingSession> findActiveSessionsByCustomerId(String customerId);
    
    /**
     * Find all shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of all shopping sessions for the customer
     */
    List<ShoppingSession> findSessionsByCustomerId(String customerId);
}