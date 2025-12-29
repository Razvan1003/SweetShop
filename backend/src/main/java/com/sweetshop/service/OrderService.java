package com.sweetshop.service;

import com.sweetshop.cart.Cart;
import com.sweetshop.cart.CartItem;
import com.sweetshop.dto.OrderRequest;
import com.sweetshop.dto.OrderResponse;
import com.sweetshop.dto.OrderView;
import com.sweetshop.entity.Address;
import com.sweetshop.entity.Order;
import com.sweetshop.entity.OrderStatus;
import com.sweetshop.entity.Product;
import com.sweetshop.entity.User;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.OrderRepository;
import com.sweetshop.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressService addressService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        AddressService addressService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressService = addressService;
    }

    public OrderResponse createOrder(User user, OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Address address = addressService.getAddressForUser(user, request.getAddressId());
        Order order = new Order(request.getQuantity(), OrderStatus.PLACED, product, user, address);
        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    public void createOrdersFromCart(User user, Cart cart, Long addressId) {
        if (cart == null || cart.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }
        Address address = addressService.getAddressForUser(user, addressId);
        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            Order order = new Order(item.getQuantity(), OrderStatus.PLACED, product, user, address);
            orderRepository.save(order);
        }
    }

    public List<OrderResponse> getOrdersForUser(User user) {
        return orderRepository.findByUserId(user.getId()).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<OrderView> getOrderViewsForUser(User user) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
            .map(order -> new OrderView(
                order.getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getProduct().getPrice(),
                order.getStatus().name(),
                order.getAddress().getAddress(),
                order.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getQuantity(),
            order.getStatus().name(),
            order.getProduct().getId(),
            order.getProduct().getName(),
            order.getProduct().getPrice(),
            order.getAddress().getId(),
            order.getCreatedAt()
        );
    }
}
