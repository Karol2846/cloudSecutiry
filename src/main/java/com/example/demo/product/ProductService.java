package com.example.demo.product;

import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

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

    public List<ProductResponse> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream().map(this::mapToResponse).toList();
    }

    public List<ProductResponse> searchProducts(String name) {
        log.info("Wyszukiwanie produktów o nazwie: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToResponse).toList();
    }

    public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice,
                                                         BigDecimal maxPrice) {
        log.info("Pobieranie produktów w przedziale cenowym: {} - {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream().map(this::mapToResponse).toList();
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request) {
        var optionalProduct = productRepository.findByName(request.name());

        if (optionalProduct.isPresent()) {
            var product = optionalProduct.get();

            var updatedStockQuantity = product.getStockQuantity() + request.stockQuantity();
            product.setStockQuantity(updatedStockQuantity);
            Product updatedProduct = productRepository.save(product);
            return mapToResponse(updatedProduct);
        } else {
            Product product = Product.builder()
                    .name(request.name())
                    .description(request.description())
                    .price(request.price())
                    .category(request.category())
                    .stockQuantity(request.stockQuantity())
                    .build();
            Product savedProduct = productRepository.save(product);
            return mapToResponse(savedProduct);
        }
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produkt o ID %s nie został znaleziony", id));

        updateProduct(request, product);
        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        log.info("Usuwanie produktu o ID: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Produkt o ID %s nie został znaleziony", id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stockQuantity(product.getStockQuantity())
                .build();
    }


    private static void updateProduct(ProductRequest request, Product product) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setCategory(request.category());
        product.setStockQuantity(request.stockQuantity());
    }
}