package com.example.demo.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {


    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Pobieranie wszystkich produktów");
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        log.info("Pobieranie produktu o ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produkt o ID %s nie został znaleziony", id));
        return mapToResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(ProductCategory category)
    {
        return productRepository.findByCategory(category).stream().map(this::mapToResponse).toList();
    }

    public List<ProductResponse> searchProducts(String name) {
        log.info("Wyszukiwanie produktów o nazwie: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToResponse).toList();
    }

    public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice,
                                                         BigDecimal maxPrice) {
        //...
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request) {
        //existsByName(name)
        //...
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        //findById(name)
        //...
    }

    public void deleteProduct(Long id) {
        //!existsById(name)
        //...
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .build();
    }
}