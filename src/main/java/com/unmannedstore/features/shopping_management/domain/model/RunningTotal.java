package com.unmannedstore.features.shopping_management.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents the running total of a shopping session, including subtotal, tax, and total.
 * This is an embeddable class that will be part of the ShoppingSession entity.
 */
@Embeddable
public class RunningTotal {
    
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    
    @Column(name = "tax", nullable = false)
    private BigDecimal tax;
    
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    
    // Default tax rate (can be configured based on location)
    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal("0.0825"); // 8.25%
    
    // Default constructor required by JPA
    public RunningTotal() {
        this.subtotal = BigDecimal.ZERO;
        this.tax = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }
    
    /**
     * Create a new running total with the specified subtotal.
     * Tax and total are calculated automatically.
     * 
     * @param subtotal The subtotal amount
     */
    public RunningTotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        recalculate();
    }
    
    /**
     * Recalculate tax and total based on the current subtotal.
     */
    public void recalculate() {
        this.tax = this.subtotal.multiply(DEFAULT_TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        this.total = this.subtotal.add(this.tax).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Add an amount to the subtotal and recalculate tax and total.
     * 
     * @param amount The amount to add
     */
    public void addToSubtotal(BigDecimal amount) {
        this.subtotal = this.subtotal.add(amount).setScale(2, RoundingMode.HALF_UP);
        recalculate();
    }
    
    /**
     * Subtract an amount from the subtotal and recalculate tax and total.
     * 
     * @param amount The amount to subtract
     */
    public void subtractFromSubtotal(BigDecimal amount) {
        this.subtotal = this.subtotal.subtract(amount).setScale(2, RoundingMode.HALF_UP);
        // Ensure subtotal doesn't go below zero
        if (this.subtotal.compareTo(BigDecimal.ZERO) < 0) {
            this.subtotal = BigDecimal.ZERO;
        }
        recalculate();
    }
    
    /**
     * Reset the running total to zero.
     */
    public void reset() {
        this.subtotal = BigDecimal.ZERO;
        this.tax = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }
    
    // Getters and setters
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        recalculate();
    }
    
    public BigDecimal getTax() {
        return tax;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    @Override
    public String toString() {
        return "RunningTotal{" +
                "subtotal=" + subtotal +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}