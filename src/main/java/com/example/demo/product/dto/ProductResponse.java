package com.example.demo.product.dto;

import com.example.demo.product.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(description = "Response payload representing a product")
@Builder
public record ProductResponse(

        @Schema(description = "Unique identifier of the product", example = "1")
        Long id,

        @Schema(description = "Name of the product", example = "Laptop Pro 15")
        String name,

        @Schema(description = "Short description of the product", example = "High-performance laptop with 16GB RAM and 512GB SSD")
        String description,

        @Schema(description = "Category the product belongs to", example = "ELECTRONICS")
        ProductCategory category,

        @Schema(description = "Price of the product", example = "1299.99")
        BigDecimal price,

        @Schema(description = "Number of units available in stock", example = "50")
        Integer stockQuantity
) {
}
