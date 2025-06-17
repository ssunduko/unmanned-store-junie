package com.unmannedstore.features.shopping_management.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Embedded;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a customer's shopping session, including basket items and running total.
 */
@Entity
@Table(name = "shopping_sessions")
public class ShoppingSession {
    
    @Id
    private String id;
    
    @Column(name = "customer_id", nullable = false)
    private String customerId;
    
    @Column(name = "store_id", nullable = false)
    private String storeId;
    
    @Column(name = "basket_id", nullable = false)
    private String basketId;
    
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;
    
    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt;
    
    @Column(name = "status", nullable = false)
    private String status; // ACTIVE, COMPLETED, CANCELLED
    
    @Embedded
    private RunningTotal runningTotal;
    
    @OneToMany(mappedBy = "shoppingSessionId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BasketItem> items;
    
    // Default constructor required by JPA
    public ShoppingSession() {
        this.items = new ArrayList<>();
        this.runningTotal = new RunningTotal();
    }
    
    /**
     * Create a new shopping session for a customer.
     * 
     * @param customerId The ID of the customer
     * @param storeId The ID of the store
     * @param basketId The ID of the basket assigned to the customer
     */
    public ShoppingSession(String customerId, String storeId, String basketId) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.storeId = storeId;
        this.basketId = basketId;
        this.startedAt = LocalDateTime.now();
        this.lastUpdatedAt = this.startedAt;
        this.status = "ACTIVE";
        this.items = new ArrayList<>();
        this.runningTotal = new RunningTotal();
    }
    
    /**
     * Add an item to the shopping session.
     * If the item already exists in the basket, increment its quantity.
     * 
     * @param product The product to add
     * @return The basket item that was added or updated
     */
    public BasketItem addItem(Product product) {
        // Check if the product is already in the basket
        for (BasketItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                // Increment quantity
                item.incrementQuantity();
                // Update running total
                runningTotal.addToSubtotal(product.getPrice());
                updateLastUpdated();
                return item;
            }
        }
        
        // Product not in basket, add new item
        BasketItem newItem = new BasketItem(
            UUID.randomUUID().toString(),
            product,
            1,
            this.id
        );
        items.add(newItem);
        
        // Update running total
        runningTotal.addToSubtotal(product.getPrice());
        updateLastUpdated();
        
        return newItem;
    }
    
    /**
     * Remove an item from the shopping session.
     * If the item has a quantity greater than 1, decrement its quantity.
     * If the quantity becomes 0, remove the item from the basket.
     * 
     * @param itemId The ID of the item to remove
     * @return The updated basket item, or null if the item was completely removed
     */
    public BasketItem removeItem(String itemId) {
        BasketItem itemToRemove = null;
        
        // Find the item
        for (BasketItem item : items) {
            if (item.getId().equals(itemId)) {
                itemToRemove = item;
                break;
            }
        }
        
        if (itemToRemove == null) {
            return null; // Item not found
        }
        
        // Decrement quantity
        boolean shouldRemove = itemToRemove.decrementQuantity();
        
        // Update running total
        runningTotal.subtractFromSubtotal(itemToRemove.getPrice());
        updateLastUpdated();
        
        if (shouldRemove) {
            // Remove the item if quantity is 0
            items.remove(itemToRemove);
            return null;
        }
        
        return itemToRemove;
    }
    
    /**
     * Complete the shopping session.
     */
    public void complete() {
        this.status = "COMPLETED";
        updateLastUpdated();
    }
    
    /**
     * Cancel the shopping session.
     */
    public void cancel() {
        this.status = "CANCELLED";
        updateLastUpdated();
    }
    
    /**
     * Update the last updated timestamp.
     */
    private void updateLastUpdated() {
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getStoreId() {
        return storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
    public String getBasketId() {
        return basketId;
    }
    
    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }
    
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public RunningTotal getRunningTotal() {
        return runningTotal;
    }
    
    public void setRunningTotal(RunningTotal runningTotal) {
        this.runningTotal = runningTotal;
    }
    
    public List<BasketItem> getItems() {
        return items;
    }
    
    public void setItems(List<BasketItem> items) {
        this.items = items;
    }
    
    /**
     * Get the number of items in the basket.
     * 
     * @return The total number of items (sum of quantities)
     */
    public int getItemCount() {
        int count = 0;
        for (BasketItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }
}