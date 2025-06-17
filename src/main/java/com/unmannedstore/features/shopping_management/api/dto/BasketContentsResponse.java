package com.unmannedstore.features.shopping_management.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for returning the contents of a shopping basket.
 */
public class BasketContentsResponse {

    private String basketId;
    private List<ProductDto> items;
    private BigDecimal runningTotal;
    private int itemCount;
    private LocalDateTime lastUpdated;

    // Default constructor
    public BasketContentsResponse() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param basketId The basket ID
     * @param items The list of items in the basket
     * @param runningTotal The running total
     * @param itemCount The number of items in the basket
     * @param lastUpdated The last updated timestamp
     */
    public BasketContentsResponse(String basketId, List<ProductDto> items, BigDecimal runningTotal, int itemCount, LocalDateTime lastUpdated) {
        this.basketId = basketId;
        this.items = items;
        this.runningTotal = runningTotal;
        this.itemCount = itemCount;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters
    public String getBasketId() {
        return basketId;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public List<ProductDto> getItems() {
        return items;
    }

    public void setItems(List<ProductDto> items) {
        this.items = items;
    }

    public BigDecimal getRunningTotal() {
        return runningTotal;
    }

    public void setRunningTotal(BigDecimal runningTotal) {
        this.runningTotal = runningTotal;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
