package com.example.demo.product.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response returned when an exception occurs")
public record ErrorResponse(

        @Schema(description = "Human-readable error message", example = "Produkt o ID 1 nie został znaleziony")
        String message
) {
}
