package com.unmannedstore.features.shopping_management.api.dto;

import java.time.LocalDateTime;

/**
 * DTO for adding an item to a shopping basket.
 */
public class ItemAddRequest {
    
    private String rfidTag;
    private String productId;
    private LocalDateTime detectedAt;
    
    // Default constructor
    public ItemAddRequest() {
    }
    
    /**
     * Constructor with all fields.
     * 
     * @param rfidTag The RFID tag of the product
     * @param productId The product ID
     * @param detectedAt The timestamp when the item was detected
     */
    public ItemAddRequest(String rfidTag, String productId, LocalDateTime detectedAt) {
        this.rfidTag = rfidTag;
        this.productId = productId;
        this.detectedAt = detectedAt;
    }
    
    // Getters and setters
    public String getRfidTag() {
        return rfidTag;
    }
    
    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }
    
    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}