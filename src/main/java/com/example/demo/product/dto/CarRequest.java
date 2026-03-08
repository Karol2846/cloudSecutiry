package com.example.demo.product.dto;

import com.example.demo.product.CarBrand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request payload for creating or updating a car")
public record CarRequest(

        @Schema(description = "Car model name", example = "Corolla", minLength = 1, maxLength = 100)
        @NotBlank(message = "Model is required")
        @Size(min = 1, max = 100, message = "Model must have between 1 and 100 characters")
        String model,

        @Schema(description = "Car brand", example = "TOYOTA")
        @NotNull(message = "Brand is required")
        CarBrand brand,

        @Schema(description = "Year of manufacture", example = "2021")
        @NotNull(message = "Year is required")
        @Min(value = 1885, message = "Year must be at least 1900")
        @Max(value = 2100, message = "Year must be at most 2100")
        Integer year,

        @Schema(description = "Mileage in kilometres", example = "45000")
        @NotNull(message = "Mileage is required")
        @PositiveOrZero(message = "Mileage must be 0 or more")
        Integer mileage,

        @Schema(description = "Car colour", example = "Pearl White")
        @NotBlank(message = "Color is required")
        @Size(max = 50, message = "Color must have at most 50 characters")
        String color,

        @Schema(description = "Asking price (must be at least 0.01)", example = "79900.00")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        BigDecimal price
) {
}
