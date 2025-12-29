package com.sweetshop.controller;

import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.entity.Role;
import com.sweetshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebAuthController {
    private final UserService userService;

    public WebAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerRequest", request);
            return "register";
        }
        try {
            userService.createUser(request, Role.CLIENT);
        } catch (DataIntegrityViolationException ex) {
            bindingResult.rejectValue("email", "duplicate", "Email already exists");
            model.addAttribute("registerRequest", request);
            return "register";
        }
        return "redirect:/login";
    }
}
