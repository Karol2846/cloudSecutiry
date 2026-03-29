package com.example.demo.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndEnabledTrue(String email);

    boolean existsByEmail(String email);
}

