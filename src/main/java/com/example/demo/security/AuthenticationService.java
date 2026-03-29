package com.example.demo.security;

import static com.example.demo.security.UserRole.*;

import com.example.demo.security.dto.AuthenticationRequest;
import com.example.demo.security.dto.AuthenticationResponse;
import com.example.demo.security.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
@RequiredArgsConstructor
class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        String normalizedEmail = normalizeEmail(request.email());

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = User.builder()
                .firstname(request.firstname().trim())
                .lastname(request.lastname().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.password()))
                .role(USER)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        User savedUser = userRepository.save(user);
        return buildResponse(savedUser);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String normalizedEmail = normalizeEmail(request.email());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.password())
        );

        User user = userRepository.findByEmailAndEnabledTrue(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        return buildResponse(user);
    }

    private AuthenticationResponse buildResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Instant expiresAt = jwtService.extractExpiration(accessToken).toInstant();

        return AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresAt(expiresAt)
                .userEmail(user.getEmail())
                .userRole(user.getRole())
                .build();
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}

