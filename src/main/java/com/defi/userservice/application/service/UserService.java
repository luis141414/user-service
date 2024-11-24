package com.defi.userservice.application.service;

import com.defi.userservice.application.dto.LoginRequestDTO;
import com.defi.userservice.application.dto.LoginResponseDTO;
import com.defi.userservice.application.dto.RegisterRequestDTO;
import com.defi.userservice.application.dto.RegisterResponseDTO;
import com.defi.userservice.application.dto.UserResponseDTO;
import com.defi.userservice.application.exception.InvalidCredentialsException;
import com.defi.userservice.application.exception.UserAlreadyExistsException;
import com.defi.userservice.application.port.out.UserRepositoryPort;
import com.defi.userservice.domain.model.User;
import com.defi.userservice.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final JwtUtil jwtUtil;
    
    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) {
        if (userRepositoryPort.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepositoryPort.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = toUserEntity(registerRequest);
        user.setRole("USER");
        User savedUser = userRepositoryPort.save(user);

        return toRegisterResponseDTO(savedUser);
    }

    private User toUserEntity(RegisterRequestDTO requestDTO) {
        return User.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .email(requestDTO.getEmail())
                .build();
    }

    private RegisterResponseDTO toRegisterResponseDTO(User user) {
        RegisterResponseDTO responseDTO = new RegisterResponseDTO();
        responseDTO.setId(String.valueOf(user.getId()));
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<User> user = userRepositoryPort.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(user.get().getUsername(),user.get().getRole());
            return toLoginResponseDTO(user.get(),token);
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }
    
    private LoginResponseDTO toLoginResponseDTO(User user, String token) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setToken(token);
        return responseDTO;
    }
    
    public UserResponseDTO getUserProfile(String username) {
        User user = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toUserResponseDTO(user);
    }
    
    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }
    
    public List<UserResponseDTO> getAllUsers() {
        return userRepositoryPort.findAll().stream()
                .map(this::toUserResponseDTO)
                .toList();
    }
}