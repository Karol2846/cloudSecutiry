package com.example.demo.product.dto;

import com.example.demo.product.ProductCategory;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must have between 2 and 100 characters")
    String name,

    @NotBlank
    @Size(max = 100, message = "Description must have at most 100 characters")
    String description,

    @NotNull
    ProductCategory category,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal price,

    @NotNull
    @PositiveOrZero
    Integer stockQuantity
) {
}
