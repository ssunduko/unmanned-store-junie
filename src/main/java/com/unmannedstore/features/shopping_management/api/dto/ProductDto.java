package com.unmannedstore.features.shopping_management.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for product information in basket responses.
 */
public class ProductDto {

    private String itemId;
    private String productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private LocalDateTime addedAt;
    private String rfidTag;
    private String imageUrl;
    private String description;
    private String category;

    // Default constructor
    public ProductDto() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param itemId The basket item ID
     * @param productId The product ID
     * @param productName The product name
     * @param price The product price
     * @param quantity The quantity in the basket
     * @param addedAt The timestamp when the item was added
     * @param rfidTag The RFID tag of the product
     * @param imageUrl The URL of the product image
     * @param description The product description
     * @param category The product category
     */
    public ProductDto(String itemId, String productId, String productName, BigDecimal price, int quantity, LocalDateTime addedAt, String rfidTag, String imageUrl, String description, String category) {
        this.itemId = itemId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.rfidTag = rfidTag;
        this.imageUrl = imageUrl;
        this.description = description;
        this.category = category;
    }

    /**
     * Constructor for basket items (without description and category).
     * 
     * @param itemId The basket item ID
     * @param productId The product ID
     * @param productName The product name
     * @param price The product price
     * @param quantity The quantity in the basket
     * @param addedAt The timestamp when the item was added
     * @param rfidTag The RFID tag of the product
     * @param imageUrl The URL of the product image
     */
    public ProductDto(String itemId, String productId, String productName, BigDecimal price, int quantity, LocalDateTime addedAt, String rfidTag, String imageUrl) {
        this(itemId, productId, productName, price, quantity, addedAt, rfidTag, imageUrl, null, null);
    }

    // Getters and setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Calculate the total price for this item (price * quantity).
     * 
     * @return The total price for this item
     */
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
