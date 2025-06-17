package com.unmannedstore.features.shopping_management.domain.service;

import com.unmannedstore.features.shopping_management.domain.model.BasketItem;
import com.unmannedstore.features.shopping_management.domain.model.RunningTotal;
import com.unmannedstore.features.shopping_management.domain.model.ShoppingSession;
import com.unmannedstore.features.shopping_management.domain.repository.ShoppingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Implementation of PricingService.
 */
@Service
public class PricingServiceImpl implements PricingService {
    
    private final ShoppingSessionRepository shoppingSessionRepository;
    
    // Default tax rate (can be configured based on location)
    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal("0.0825"); // 8.25%
    
    /**
     * Constructor for PricingServiceImpl.
     * 
     * @param shoppingSessionRepository The shopping session repository
     */
    public PricingServiceImpl(ShoppingSessionRepository shoppingSessionRepository) {
        this.shoppingSessionRepository = shoppingSessionRepository;
    }
    
    /**
     * Calculate the subtotal for a list of basket items.
     * 
     * @param items The list of basket items
     * @return The subtotal
     */
    @Override
    public BigDecimal calculateSubtotal(List<BasketItem> items) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (BasketItem item : items) {
            subtotal = subtotal.add(item.getTotalPrice());
        }
        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate the tax for a subtotal.
     * 
     * @param subtotal The subtotal
     * @return The tax amount
     */
    @Override
    public BigDecimal calculateTax(BigDecimal subtotal) {
        return subtotal.multiply(DEFAULT_TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate the total for a subtotal and tax.
     * 
     * @param subtotal The subtotal
     * @param tax The tax amount
     * @return The total
     */
    @Override
    public BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax) {
        return subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Apply any applicable discounts to a shopping session.
     * 
     * @param session The shopping session
     * @return The updated shopping session with discounts applied
     */
    @Override
    @Transactional
    public ShoppingSession applyDiscounts(ShoppingSession session) {
        // In a real implementation, this would apply various discount rules
        // For now, we'll just return the session unchanged
        return session;
    }
    
    /**
     * Update the running total for a shopping session.
     * 
     * @param session The shopping session
     * @return The updated shopping session with recalculated running total
     */
    @Override
    @Transactional
    public ShoppingSession updateRunningTotal(ShoppingSession session) {
        // Calculate subtotal
        BigDecimal subtotal = calculateSubtotal(session.getItems());
        
        // Update the running total
        RunningTotal runningTotal = session.getRunningTotal();
        runningTotal.setSubtotal(subtotal);
        
        // Save and return the updated session
        return shoppingSessionRepository.save(session);
    }
}