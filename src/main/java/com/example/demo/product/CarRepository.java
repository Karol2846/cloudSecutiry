package com.example.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByBrand(CarBrand brand);

    List<Car> findByModelContainingIgnoreCase(String model);

    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Car> findByYear(Integer year);

    @Query("SELECT c FROM Car c WHERE c.price <= :maxPrice AND c.brand = :brand")
    List<Car> findByBrandAndMaxPrice(
            @Param("brand") CarBrand brand,
            @Param("maxPrice") BigDecimal maxPrice);
}