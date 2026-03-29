package com.example.demo.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Firstname is required")
        @Size(min = 2, max = 50, message = "Firstname must have between 2 and 50 characters")
        String firstname,

        @NotBlank(message = "Lastname is required")
        @Size(min = 2, max = 50, message = "Lastname must have between 2 and 50 characters")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must have at least 8 characters")
        String password
) {
}

