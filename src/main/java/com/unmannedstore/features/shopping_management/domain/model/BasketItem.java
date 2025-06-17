package com.unmannedstore.features.shopping_management.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an item in a customer's shopping basket.
 */
@Entity
@Table(name = "basket_items")
public class BasketItem {
    @Id
    private String id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
    
    @Column(name = "shopping_session_id", nullable = false)
    private String shoppingSessionId;
    
    // Default constructor required by JPA
    public BasketItem() {
    }
    
    public BasketItem(String id, Product product, Integer quantity, String shoppingSessionId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
        this.addedAt = LocalDateTime.now();
        this.shoppingSessionId = shoppingSessionId;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
        this.price = product.getPrice();
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }
    
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public String getShoppingSessionId() {
        return shoppingSessionId;
    }
    
    public void setShoppingSessionId(String shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
    }
    
    /**
     * Calculate the total price for this basket item (price * quantity).
     * 
     * @return The total price for this item
     */
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    /**
     * Increment the quantity of this basket item by 1.
     */
    public void incrementQuantity() {
        this.quantity += 1;
    }
    
    /**
     * Decrement the quantity of this basket item by 1.
     * 
     * @return true if the quantity is now 0, false otherwise
     */
    public boolean decrementQuantity() {
        this.quantity -= 1;
        return this.quantity <= 0;
    }
}