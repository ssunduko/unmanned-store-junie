package com.unmannedstore.features.shopping_management.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event that is published when an item is removed from a shopping basket.
 */
public class ItemRemovedEvent {
    
    private final String basketId;
    private final String productId;
    private final String itemId;
    private final BigDecimal runningTotal;
    private final LocalDateTime timestamp;
    
    /**
     * Constructor for ItemRemovedEvent.
     * 
     * @param basketId The basket ID
     * @param productId The product ID
     * @param itemId The basket item ID
     * @param runningTotal The running total after removing the item
     */
    public ItemRemovedEvent(String basketId, String productId, String itemId, BigDecimal runningTotal) {
        this.basketId = basketId;
        this.productId = productId;
        this.itemId = itemId;
        this.runningTotal = runningTotal;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getBasketId() {
        return basketId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public BigDecimal getRunningTotal() {
        return runningTotal;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return "ItemRemovedEvent{" +
                "basketId='" + basketId + '\'' +
                ", productId='" + productId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", runningTotal=" + runningTotal +
                ", timestamp=" + timestamp +
                '}';
    }
}