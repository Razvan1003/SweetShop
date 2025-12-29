package com.sweetshop.controller;

import com.sweetshop.entity.User;
import com.sweetshop.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderPageController {
    private final OrderService orderService;

    public OrderPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/me/orders")
    public String orders(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orders", orderService.getOrderViewsForUser(user));
        return "orders";
    }

}
