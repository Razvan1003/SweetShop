package com.sweetshop.controller;

import com.sweetshop.cart.AddToCartRequest;
import com.sweetshop.cart.Cart;
import com.sweetshop.cart.CartService;
import com.sweetshop.cart.CheckoutRequest;
import com.sweetshop.dto.ProductResponse;
import com.sweetshop.entity.User;
import com.sweetshop.service.AddressService;
import com.sweetshop.service.OrderService;
import com.sweetshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
@PreAuthorize("hasRole('CLIENT')")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final AddressService addressService;
    private final OrderService orderService;

    public CartController(CartService cartService, ProductService productService,
                          AddressService addressService, OrderService orderService) {
        this.cartService = cartService;
        this.productService = productService;
        this.addressService = addressService;
        this.orderService = orderService;
    }

    @GetMapping
    public String view(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.getTotal());
        model.addAttribute("addresses", addressService.getAddresses(user));
        model.addAttribute("checkoutRequest", new CheckoutRequest());
        return "cart";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("addToCart") AddToCartRequest request,
                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productService.getById(request.getProductId()));
            return "product";
        }
        ProductResponse product = productService.getById(request.getProductId());
        cartService.addProduct(product, request.getQuantity());
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long productId) {
        cartService.removeProduct(productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("checkoutRequest") CheckoutRequest request,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getCart();
        if (cart.isEmpty()) {
            bindingResult.reject("cart", "Cosul este gol");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("total", cart.getTotal());
            model.addAttribute("addresses", addressService.getAddresses(user));
            return "cart";
        }
        orderService.createOrdersFromCart(user, cart, request.getAddressId());
        cartService.clear();
        return "redirect:/me/orders";
    }
}
