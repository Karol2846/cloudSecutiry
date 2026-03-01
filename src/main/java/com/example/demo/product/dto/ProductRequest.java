package com.example.demo.product.dto;

import com.example.demo.product.ProductCategory;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    String description,
    ProductCategory category,
    BigDecimal price,
    Integer stockQuantity
) {
}
