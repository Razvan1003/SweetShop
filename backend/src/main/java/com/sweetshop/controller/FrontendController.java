package com.sweetshop.controller;

import com.sweetshop.service.CategoryService;
import com.sweetshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontendController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public FrontendController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String query,
                        @RequestParam(required = false) Long categoryId,
                        Model model) {
        model.addAttribute("products", productService.search(query, categoryId));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("query", query == null ? "" : query);
        model.addAttribute("categoryId", categoryId);
        return "index";
    }
}
