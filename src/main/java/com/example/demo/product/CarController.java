package com.example.demo.product;

import com.example.demo.product.dto.CarRequest;
import com.example.demo.product.dto.CarResponse;
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
@RequestMapping("/api/v1/cars")
class CarController implements CarControllerApi {

    private final CarCrudService carService;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        log.info("GET /api/v1/cars - Pobieranie wszystkich samochodów");
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        log.info("GET /api/v1/cars/{} - Pobieranie samochodu", id);
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CarResponse>> getCarsByBrand(@PathVariable CarBrand brand) {
        return ResponseEntity.ok(carService.getCarsByBrand(brand));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarResponse>> searchCarsByModel(@RequestParam String model) {
        return ResponseEntity.ok(carService.searchCarsByModel(model));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CarResponse>> getCarsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(carService.getCarsByPriceRange(minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest request) {
        return new ResponseEntity<>(carService.createCar(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id,
                                                  @Valid @RequestBody CarRequest request) {
        return new ResponseEntity<>(carService.updateCar(id, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.info("DELETE /api/v1/cars/{} - Usuwanie samochodu", id);
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}