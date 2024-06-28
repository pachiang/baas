package com.baas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Password is mandatory")
    private String password;
}