package com.sweetshop.controller;

import com.sweetshop.dto.AddressRequest;
import com.sweetshop.dto.AddressResponse;
import com.sweetshop.entity.User;
import com.sweetshop.service.AddressService;
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
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<AddressResponse> create(@Valid @RequestBody AddressRequest request) {
        User user = userService.getCurrentUser();
        AddressResponse response = addressService.addAddress(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my")
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(addressService.getAddresses(user));
    }
}
