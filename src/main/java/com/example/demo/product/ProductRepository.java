package com.example.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.price <= :maxPrice AND p.category= :category")
    List<Product> findByCategoryAndMaxPrice(
            @Param("category") ProductCategory category,
            @Param("maxPrice") BigDecimal maxPrice);

    Optional<Product> findByName(String name);
}