package com.example.demo.product;

import com.example.demo.product.dto.CarRequest;
import com.example.demo.product.dto.CarResponse;
import com.example.demo.product.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Cars", description = "Car catalog management API")
@SecurityRequirement(name = "bearerAuth")
interface CarControllerApi {

    @Operation(summary = "Get all cars", description = "Retrieves a list of all cars in the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<CarResponse>> getAllCars();

    @Operation(summary = "Get car by ID", description = "Retrieves a single car by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car found",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<CarResponse> getCarById(
            @Parameter(description = "Car identifier", example = "1", required = true) Long id);

    @Operation(summary = "Get cars by brand", description = "Retrieves all cars of a given brand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<CarResponse>> getCarsByBrand(
            @Parameter(description = "Car brand", example = "TOYOTA", required = true) CarBrand brand);

    @Operation(summary = "Search cars by model", description = "Searches for cars whose model name contains the given phrase (case-insensitive)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<CarResponse>> searchCarsByModel(
            @Parameter(description = "Search phrase for car model", example = "Corolla", required = true) String model);

    @Operation(summary = "Get cars by price range", description = "Retrieves all cars with price between minPrice and maxPrice (inclusive)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cars retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<CarResponse>> getCarsByPriceRange(
            @Parameter(description = "Minimum price (inclusive)", example = "10000.00", required = true) BigDecimal minPrice,
            @Parameter(description = "Maximum price (inclusive)", example = "150000.00", required = true) BigDecimal maxPrice);

    @Operation(summary = "Create a new car", description = "Adds a new car to the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Car created successfully",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<CarResponse> createCar(CarRequest request);

    @Operation(summary = "Update an existing car", description = "Updates all fields of an existing car by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Car updated successfully",
                    content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<CarResponse> updateCar(
            @Parameter(description = "Car identifier", example = "1", required = true) Long id,
            CarRequest request);

    @Operation(summary = "Delete a car", description = "Permanently removes a car from the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> deleteCar(
            @Parameter(description = "Car identifier", example = "1", required = true) Long id);
}
