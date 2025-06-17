package com.unmannedstore.features.shopping_management.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

/**
 * Represents a product in the store inventory that can be added to a shopping basket.
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "RFIDTAG", nullable = false, unique = true)
    private String rfidTag;

    @Column
    private String description;

    @Column
    private String category;

    @Column(name = "IMAGEURL")
    private String imageUrl;

    // Default constructor required by JPA
    public Product() {
    }

    public Product(String id, String name, BigDecimal price, String rfidTag) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rfidTag = rfidTag;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
