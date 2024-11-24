package com.defi.userservice.infrastructure.adapter.in;

import com.defi.userservice.application.dto.LoginRequestDTO;
import com.defi.userservice.application.dto.LoginResponseDTO;
import com.defi.userservice.application.dto.RegisterRequestDTO;
import com.defi.userservice.application.dto.RegisterResponseDTO;
import com.defi.userservice.application.dto.UserResponseDTO;
import com.defi.userservice.application.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO ) {
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUserProfile() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponseDTO userProfile = userService.getUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }
}