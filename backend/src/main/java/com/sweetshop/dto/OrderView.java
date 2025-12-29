package com.sweetshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderView {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private String status;
    private String addressLine;
    private LocalDateTime createdAt;

    public OrderView(Long id, String productName, int quantity, BigDecimal unitPrice, String status,
                     String addressLine, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
        this.addressLine = addressLine;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
