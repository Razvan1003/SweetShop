package com.sweetshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private int quantity;
    private String status;
    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private Long addressId;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, int quantity, String status, Long productId, String productName,
                         BigDecimal unitPrice, Long addressId, LocalDateTime createdAt) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.addressId = addressId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Long getAddressId() {
        return addressId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
