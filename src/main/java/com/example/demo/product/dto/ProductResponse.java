package com.example.demo.product.dto;

import com.example.demo.product.ProductCategory;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        ProductCategory category,
        BigDecimal price,
        Integer stockQuantity
) {
}
