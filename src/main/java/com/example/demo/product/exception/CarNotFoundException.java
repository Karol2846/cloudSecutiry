package com.example.demo.product.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message, Object... args) {
        super(message.formatted(args));
    }
}
