package com.example.demo.product;

import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Products", description = "Product catalog management API")
interface ProductControllerApi {

    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ProductResponse>> getAllProducts();

    @Operation(summary = "Get product by ID", description = "Retrieves a single product by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product identifier", example = "1", required = true) Long id);

    @Operation(summary = "Get products by category", description = "Retrieves all products belonging to a specific category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "Product category", example = "ELECTRONICS", required = true) ProductCategory category);

    @Operation(summary = "Search products by name", description = "Searches for products whose name contains the given phrase (case-insensitive)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Search phrase for product name", example = "Laptop", required = true) String name);

    @Operation(summary = "Get products by price range", description = "Retrieves all products with price between minPrice and maxPrice (inclusive)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @Parameter(description = "Minimum price (inclusive)", example = "100.00", required = true) BigDecimal minPrice,
            @Parameter(description = "Maximum price (inclusive)", example = "2000.00", required = true) BigDecimal maxPrice);

    @Operation(summary = "Create a new product", description = "Creates a new product. If a product with the same name already exists, its stock quantity is increased instead.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ProductResponse> createProduct(ProductRequest request);

    @Operation(summary = "Update an existing product", description = "Updates all fields of an existing product by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product identifier", example = "1", required = true) Long id,
            ProductRequest request);

    @Operation(summary = "Delete a product", description = "Permanently removes a product from the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product identifier", example = "1", required = true) Long id);
}
