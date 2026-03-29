package com.example.demo.security;

import com.example.demo.security.dto.AuthenticationRequest;
import com.example.demo.security.dto.AuthenticationResponse;
import com.example.demo.security.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register account", description = "Creates a new USER account")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT tokens")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

