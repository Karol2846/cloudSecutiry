package com.example.demo.security.dto;

import com.example.demo.security.UserRole;
import lombok.Builder;

import java.time.Instant;

@Builder
public record AuthenticationResponse(
        String token,
        String refreshToken,
        String tokenType,
        Instant expiresAt,
        String userEmail,
        UserRole userRole
) {
    public AuthenticationResponse {
        if (tokenType == null || tokenType.isBlank()) {
            tokenType = "Bearer";
        }
    }
}


