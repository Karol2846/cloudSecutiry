package com.example.demo.product.dto;

import com.example.demo.product.CarBrand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(description = "Response payload representing a car")
@Builder
public record CarResponse(

        @Schema(description = "Unique identifier of the car", example = "1")
        Long id,

        @Schema(description = "Car model name", example = "Corolla")
        String model,

        @Schema(description = "Car brand", example = "TOYOTA")
        CarBrand brand,

        @Schema(description = "Year of manufacture", example = "2021")
        Integer year,

        @Schema(description = "Mileage in kilometres", example = "45000")
        Integer mileage,

        @Schema(description = "Car colour", example = "Pearl White")
        String color,

        @Schema(description = "Vehicle Identification Number (VIN)", example = "1HGBH41JXMN109186")
        String vin,

        @Schema(description = "Asking price", example = "79900.00")
        BigDecimal price
) {
}
