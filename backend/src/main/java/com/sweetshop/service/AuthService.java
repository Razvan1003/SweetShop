package com.sweetshop.service;

import com.sweetshop.config.JwtService;
import com.sweetshop.dto.AuthRequest;
import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.entity.Role;
import com.sweetshop.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = userService.createUser(request, Role.CLIENT);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole().name(), user.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userService.getByEmail(request.getEmail());
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole().name(), user.getEmail());
    }
}
