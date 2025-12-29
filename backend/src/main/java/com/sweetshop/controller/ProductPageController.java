package com.sweetshop.controller;

import com.sweetshop.cart.AddToCartRequest;
import com.sweetshop.dto.ProductResponse;
import com.sweetshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductPageController {
    private final ProductService productService;

    public ProductPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        ProductResponse product = productService.getById(id);
        model.addAttribute("product", product);

        AddToCartRequest addToCart = new AddToCartRequest();
        addToCart.setProductId(product.getId());
        addToCart.setQuantity(1);
        model.addAttribute("addToCart", addToCart);
        return "product";
    }
}
