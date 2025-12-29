package com.sweetshop.controller;

import com.sweetshop.dto.ProductRequest;
import com.sweetshop.dto.ProductResponse;
import com.sweetshop.service.CategoryService;
import com.sweetshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/products";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("pageTitle", "Produs nou");
        model.addAttribute("formAction", "/admin/products/new");
        return "admin/product-form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("product") ProductRequest request,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("pageTitle", "Produs nou");
            model.addAttribute("formAction", "/admin/products/new");
            return "admin/product-form";
        }
        productService.create(request);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProductResponse product = productService.getById(id);
        ProductRequest form = new ProductRequest();
        form.setName(product.getName());
        form.setDescription(product.getDescription());
        form.setPrice(product.getPrice());
        form.setCategoryId(product.getCategoryId());

        model.addAttribute("product", form);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("pageTitle", "Editeaza produs");
        model.addAttribute("formAction", "/admin/products/" + id + "/edit");
        return "admin/product-form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("product") ProductRequest request,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("pageTitle", "Editeaza produs");
            model.addAttribute("formAction", "/admin/products/" + id + "/edit");
            return "admin/product-form";
        }
        productService.update(id, request);
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
