package com.example.demo.product;

import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("GET /api/v1/products - Pobieranie wszystkich produktów");
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        log.info("GET /api/v1/products/{} - Pobieranie produktu", id);
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable ProductCategory category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
    return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.updateProduct(id, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("DELETE /api/v1/products/{} - Usuwanie produktu", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}