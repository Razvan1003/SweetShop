package com.sweetshop.cart;

import com.sweetshop.dto.ProductResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "CART";

    private final HttpSession session;

    public CartService(HttpSession session) {
        this.session = session;
    }

    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addProduct(ProductResponse product, int quantity) {
        if (quantity <= 0) {
            return;
        }
        CartItem item = new CartItem(
            product.getId(),
            product.getName(),
            product.getCategoryName(),
            product.getPrice(),
            quantity
        );
        getCart().addItem(item);
    }

    public void updateQuantity(Long productId, int quantity) {
        getCart().updateQuantity(productId, quantity);
    }

    public void removeProduct(Long productId) {
        getCart().removeItem(productId);
    }

    public void clear() {
        getCart().clear();
    }
}
