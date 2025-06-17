package com.unmannedstore.features.shopping_management.infrastructure.persistence;

import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of ShoppingSessionRepository.
 */
@Repository
public interface JpaShoppingSessionRepository extends JpaRepository<ShoppingSession, String>, ShoppingSessionRepository {
    
    /**
     * Find active shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of active shopping sessions for the customer
     */
    @Query("SELECT s FROM ShoppingSession s WHERE s.customerId = :customerId AND s.status = 'ACTIVE'")
    List<ShoppingSession> findActiveSessionsByCustomerId(@Param("customerId") String customerId);
    
    /**
     * Find a shopping session by basket ID.
     * 
     * @param basketId The basket ID
     * @return An Optional containing the shopping session if found, or empty if not found
     */
    @Override
    Optional<ShoppingSession> findByBasketId(String basketId);
    
    /**
     * Find all shopping sessions for a customer.
     * 
     * @param customerId The customer ID
     * @return A list of all shopping sessions for the customer
     */
    @Override
    List<ShoppingSession> findByCustomerId(String customerId);
    
    /**
     * Find all shopping sessions for a store.
     * 
     * @param storeId The store ID
     * @return A list of all shopping sessions for the store
     */
    @Override
    List<ShoppingSession> findByStoreId(String storeId);
}