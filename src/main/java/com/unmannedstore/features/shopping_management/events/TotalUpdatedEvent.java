package com.unmannedstore.features.shopping_management.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event that is published when the running total of a shopping basket is updated.
 */
public class TotalUpdatedEvent {
    
    private final String basketId;
    private final BigDecimal oldTotal;
    private final BigDecimal newTotal;
    private final int itemCount;
    private final LocalDateTime timestamp;
    
    /**
     * Constructor for TotalUpdatedEvent.
     * 
     * @param basketId The basket ID
     * @param oldTotal The old running total
     * @param newTotal The new running total
     * @param itemCount The number of items in the basket
     */
    public TotalUpdatedEvent(String basketId, BigDecimal oldTotal, BigDecimal newTotal, int itemCount) {
        this.basketId = basketId;
        this.oldTotal = oldTotal;
        this.newTotal = newTotal;
        this.itemCount = itemCount;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getBasketId() {
        return basketId;
    }
    
    public BigDecimal getOldTotal() {
        return oldTotal;
    }
    
    public BigDecimal getNewTotal() {
        return newTotal;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Calculate the difference between the old and new totals.
     * 
     * @return The difference (new total - old total)
     */
    public BigDecimal getDifference() {
        return newTotal.subtract(oldTotal);
    }
    
    @Override
    public String toString() {
        return "TotalUpdatedEvent{" +
                "basketId='" + basketId + '\'' +
                ", oldTotal=" + oldTotal +
                ", newTotal=" + newTotal +
                ", itemCount=" + itemCount +
                ", timestamp=" + timestamp +
                '}';
    }
}