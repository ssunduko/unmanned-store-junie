package com.unmannedstore.features.shopping_management.api.dto;

import java.math.BigDecimal;

/**
 * DTO for returning the result of adding or removing an item from a shopping basket.
 */
public class BasketUpdateResponse {
    
    private String basketId;
    private String action;  // "item_added" or "item_removed"
    private ProductDto item;
    private BigDecimal newTotal;
    private int itemCount;
    private String notification;
    
    // Default constructor
    public BasketUpdateResponse() {
    }
    
    /**
     * Constructor with all fields.
     * 
     * @param basketId The basket ID
     * @param action The action performed ("item_added" or "item_removed")
     * @param item The item that was added or removed
     * @param newTotal The new total after the update
     * @param itemCount The new item count after the update
     * @param notification A notification message
     */
    public BasketUpdateResponse(String basketId, String action, ProductDto item, BigDecimal newTotal, int itemCount, String notification) {
        this.basketId = basketId;
        this.action = action;
        this.item = item;
        this.newTotal = newTotal;
        this.itemCount = itemCount;
        this.notification = notification;
    }
    
    // Getters and setters
    public String getBasketId() {
        return basketId;
    }
    
    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public ProductDto getItem() {
        return item;
    }
    
    public void setItem(ProductDto item) {
        this.item = item;
    }
    
    public BigDecimal getNewTotal() {
        return newTotal;
    }
    
    public void setNewTotal(BigDecimal newTotal) {
        this.newTotal = newTotal;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public String getNotification() {
        return notification;
    }
    
    public void setNotification(String notification) {
        this.notification = notification;
    }
}