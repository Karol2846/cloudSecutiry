package com.example.demo.product.dto;

import com.example.demo.product.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request payload for creating or updating a product")
public record ProductRequest(

        @Schema(description = "Name of the product", example = "Laptop Pro 15", minLength = 2, maxLength = 100)
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must have between 2 and 100 characters")
        String name,

        @Schema(description = "Short description of the product", example = "High-performance laptop with 16GB RAM and 512GB SSD", maxLength = 100)
        @NotBlank
        @Size(max = 100, message = "Description must have at most 100 characters")
        String description,

        @Schema(description = "Category the product belongs to", example = "ELECTRONICS")
        @NotNull
        ProductCategory category,

        @Schema(description = "Price of the product (must be at least 0.01)", example = "1299.99")
        @NotNull
        @DecimalMin("0.01")
        BigDecimal price,

        @Schema(description = "Number of units available in stock (must be 0 or more)", example = "50")
        @NotNull
        @PositiveOrZero
        Integer stockQuantity
) {
}
