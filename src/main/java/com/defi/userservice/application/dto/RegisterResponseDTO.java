package com.defi.userservice.application.dto;

import lombok.Data;

@Data
public class RegisterResponseDTO {
    private String id;
    private String username;
    private String email;
}