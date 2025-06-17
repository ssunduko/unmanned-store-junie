package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for pricing-related operations.
 */
public interface PricingService {
    
    /**
     * Calculate the subtotal for a list of basket items.
     * 
     * @param items The list of basket items
     * @return The subtotal
     */
    BigDecimal calculateSubtotal(List<BasketItem> items);
    
    /**
     * Calculate the tax for a subtotal.
     * 
     * @param subtotal The subtotal
     * @return The tax amount
     */
    BigDecimal calculateTax(BigDecimal subtotal);
    
    /**
     * Calculate the total for a subtotal and tax.
     * 
     * @param subtotal The subtotal
     * @param tax The tax amount
     * @return The total
     */
    BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax);
    
    /**
     * Apply any applicable discounts to a shopping session.
     * 
     * @param session The shopping session
     * @return The updated shopping session with discounts applied
     */
    ShoppingSession applyDiscounts(ShoppingSession session);
    
    /**
     * Update the running total for a shopping session.
     * 
     * @param session The shopping session
     * @return The updated shopping session with recalculated running total
     */
    ShoppingSession updateRunningTotal(ShoppingSession session);
}