package com.sweetshop.cart;

import java.math.BigDecimal;

public class CartItem {
    private Long productId;
    private String name;
    private String categoryName;
    private BigDecimal price;
    private int quantity;

    public CartItem(Long productId, String name, String categoryName, BigDecimal price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.categoryName = categoryName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
