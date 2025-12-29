package com.sweetshop.config;

import com.sweetshop.cart.CartService;
import java.util.Set;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
    private final CartService cartService;

    public GlobalModelAttributes(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute
    public void addGlobalAttributes(Authentication authentication, Model model) {
        boolean isAuthenticated = authentication != null
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken);

        Set<String> roles = Set.of();
        if (isAuthenticated) {
            roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.toSet());
        }

        boolean isAdmin = roles.contains("ROLE_ADMIN");
        boolean isClient = roles.contains("ROLE_CLIENT");

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isClient", isClient);
        model.addAttribute("cartCount", isClient ? cartService.getCart().getTotalQuantity() : 0);
    }
}
