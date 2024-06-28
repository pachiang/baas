package com.baas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull(message = "Account is required")
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotNull(message = "Account is required")
    @NotBlank(message = "Account is mandatory")
    private String password;
}