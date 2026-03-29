package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@Slf4j
@Configuration
@RequiredArgsConstructor
class AdminSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedAdmin(@Value("${security.admin.email:admin@cloud.local}") String adminEmail,
                                @Value("${security.admin.password:Admin123!}") String adminPassword,
                                @Value("${security.admin.firstname:System}") String adminFirstname,
                                @Value("${security.admin.lastname:Admin}") String adminLastname) {
        return args -> {
            String normalizedEmail = normalizeEmail(adminEmail);
            if (userRepository.existsByEmail(normalizedEmail)) {
                return;
            }

            User admin = User.builder()
                    .firstname(adminFirstname)
                    .lastname(adminLastname)
                    .email(normalizedEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(UserRole.ADMIN)
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();

            userRepository.save(admin);
            log.info("Created default admin account: {}", normalizedEmail);
        };
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }
}

