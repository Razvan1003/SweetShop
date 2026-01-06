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

    public FrontendController(ProductService productService,
                              CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /**
     * Landing page – doar prezentare, hero + avantajele.
     * NU încărcăm produse aici, doar returnăm index.html.
     */
    @GetMapping("/")
    public String home() {
        return "index";   // templates/index.html
    }

    /**
     * Pagina de meniu – aici apar produsele + filtrele.
     */
    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) String query,
                       @RequestParam(required = false) Long categoryId,
                       Model model) {

        // produse filtrate
        model.addAttribute("products", productService.search(query, categoryId));

        // categorii pentru dropdown
        model.addAttribute("categories", categoryService.getAll());

        // ca să păstrăm valorile în câmpuri după filtrare
        model.addAttribute("query", query == null ? "" : query);
        model.addAttribute("categoryId", categoryId);

        return "menu";    // templates/menu.html
    }
}
