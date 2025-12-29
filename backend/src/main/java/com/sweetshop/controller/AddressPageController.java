package com.sweetshop.controller;

import com.sweetshop.dto.AddressRequest;
import com.sweetshop.entity.User;
import com.sweetshop.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasRole('CLIENT')")
public class AddressPageController {
    private final AddressService addressService;

    public AddressPageController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/me/addresses")
    public String addresses(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("addresses", addressService.getAddresses(user));
        model.addAttribute("addressForm", new AddressRequest());
        return "addresses";
    }

    @PostMapping("/me/addresses")
    public String addAddress(@Valid @ModelAttribute("addressForm") AddressRequest request,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal User user, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addresses", addressService.getAddresses(user));
            return "addresses";
        }
        addressService.addAddress(user, request);
        return "redirect:/me/addresses";
    }
}
