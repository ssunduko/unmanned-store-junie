package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.Product;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ProductRepository;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ShoppingService.
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {
    
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ProductRepository productRepository;
    
    /**
     * Constructor for ShoppingServiceImpl.
     * 
     * @param shoppingSessionRepository The shopping session repository
     * @param productRepository The product repository
     */
    public ShoppingServiceImpl(ShoppingSessionRepository shoppingSessionRepository, ProductRepository productRepository) {
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.productRepository = productRepository;
    }
    
    /**
     * Create a new shopping session.
     * 
     * @param customerId The ID of the customer
     * @param storeId The ID of the store
     * @param basketId The ID of the basket assigned to the customer
     * @return The created shopping session
     */
    @Override
    @Transactional
    public ShoppingSession createSession(String customerId, String storeId, String basketId) {
        // Check if there's already an active session for this basket
        Optional<ShoppingSession> existingSession = shoppingSessionRepository.findByBasketId(basketId);
        if (existingSession.isPresent() && "ACTIVE".equals(existingSession.get().getStatus())) {
            return existingSession.get();
        }
        
        // Create a new session
        ShoppingSession session = new ShoppingSession(customerId, storeId, basketId);
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Find a shopping session by its ID.
     * 
     * @param sessionId The shopping session ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingSession> findSessionById(String sessionId) {
        return shoppingSessionRepository.findById(sessionId);
    }
    
    /**
     * Find a shopping session by basket ID.
     * 
     * @param basketId The basket ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingSession> findSessionByBasketId(String basketId) {
        return shoppingSessionRepository.findByBasketId(basketId);
    }
    
    /**
     * Add an item to a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @param productId The product ID
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or product is not found
     */
    @Override
    @Transactional
    public ShoppingSession addItemToSession(String sessionId, String productId) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        
        session.addItem(product);
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Add an item to a shopping session by RFID tag.
     * 
     * @param sessionId The shopping session ID
     * @param rfidTag The RFID tag of the product
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or product is not found
     */
    @Override
    @Transactional
    public ShoppingSession addItemToSessionByRfidTag(String sessionId, String rfidTag) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        Product product = productRepository.findByRfidTag(rfidTag)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with RFID tag: " + rfidTag));
        
        session.addItem(product);
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Remove an item from a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @param itemId The basket item ID
     * @return The updated shopping session
     * @throws IllegalArgumentException if the session or item is not found
     */
    @Override
    @Transactional
    public ShoppingSession removeItemFromSession(String sessionId, String itemId) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        session.removeItem(itemId);
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Get all items in a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return A list of basket items
     * @throws IllegalArgumentException if the session is not found
     */
    @Override
    @Transactional(readOnly = true)
    public List<BasketItem> getSessionItems(String sessionId) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        return session.getItems();
    }
    
    /**
     * Complete a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return The completed shopping session
     * @throws IllegalArgumentException if the session is not found
     */
    @Override
    @Transactional
    public ShoppingSession completeSession(String sessionId) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        session.complete();
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Cancel a shopping session.
     * 
     * @param sessionId The shopping session ID
     * @return The cancelled shopping session
     * @throws IllegalArgumentException if the session is not found
     */
    @Override
    @Transactional
    public ShoppingSession cancelSession(String sessionId) {
        ShoppingSession session = shoppingSessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Shopping session not found: " + sessionId));
        
        session.cancel();
        return shoppingSessionRepository.save(session);
    }
    
    /**
     * Find active shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of active shopping sessions for the customer
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingSession> findActiveSessionsByCustomerId(String customerId) {
        return shoppingSessionRepository.findActiveSessionsByCustomerId(customerId);
    }
    
    /**
     * Find all shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of all shopping sessions for the customer
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingSession> findSessionsByCustomerId(String customerId) {
        return shoppingSessionRepository.findByCustomerId(customerId);
    }
}