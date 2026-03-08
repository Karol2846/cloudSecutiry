package com.example.demo.product;

import com.example.demo.product.dto.CarRequest;
import com.example.demo.product.dto.CarResponse;
import com.example.demo.product.exception.CarNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CarCrudService {

    private final CarRepository carRepository;

    public List<CarResponse> getAllCars() {
        log.info("Pobieranie wszystkich samochodów");
        return carRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CarResponse getCarById(Long id) {
        log.info("Pobieranie samochodu o ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Samochód o ID %s nie został znaleziony", id));
        return mapToResponse(car);
    }

    public List<CarResponse> getCarsByBrand(CarBrand brand) {
        return carRepository.findByBrand(brand).stream().map(this::mapToResponse).toList();
    }

    public List<CarResponse> searchCarsByModel(String model) {
        log.info("Wyszukiwanie samochodów o modelu: {}", model);
        return carRepository.findByModelContainingIgnoreCase(model).stream().map(this::mapToResponse).toList();
    }

    public List<CarResponse> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Pobieranie samochodów w przedziale cenowym: {} - {}", minPrice, maxPrice);
        return carRepository.findByPriceBetween(minPrice, maxPrice).stream().map(this::mapToResponse).toList();
    }

    public CarResponse createCar(CarRequest request) {
        Car car = Car.builder()
                .model(request.model())
                .brand(request.brand())
                .year(request.year())
                .mileage(request.mileage())
                .color(request.color())
                .price(request.price())
                .build();
        Car savedCar = carRepository.save(car);
        log.info("Utworzono samochód o ID: {}", savedCar.getId());
        return mapToResponse(savedCar);
    }

    public CarResponse updateCar(Long id, CarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Samochód o ID %s nie został znaleziony", id));

        car.setModel(request.model());
        car.setBrand(request.brand());
        car.setYear(request.year());
        car.setMileage(request.mileage());
        car.setColor(request.color());
        car.setPrice(request.price());

        Car updatedCar = carRepository.save(car);
        return mapToResponse(updatedCar);
    }

    public void deleteCar(Long id) {
        log.info("Usuwanie samochodu o ID: {}", id);
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException("Samochód o ID %s nie został znaleziony", id);
        }
        carRepository.deleteById(id);
    }

    private CarResponse mapToResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .year(car.getYear())
                .mileage(car.getMileage())
                .color(car.getColor())
                .price(car.getPrice())
                .build();
    }
}