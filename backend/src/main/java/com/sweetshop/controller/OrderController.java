package com.sweetshop.controller;

import com.sweetshop.dto.OrderRequest;
import com.sweetshop.dto.OrderResponse;
import com.sweetshop.entity.User;
import com.sweetshop.service.OrderService;
import com.sweetshop.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        User user = userService.getCurrentUser();
        OrderResponse response = orderService.createOrder(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(orderService.getOrdersForUser(user));
    }

}
