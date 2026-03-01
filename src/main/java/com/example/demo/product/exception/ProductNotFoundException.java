package com.example.demo.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message, Object... args) {
        super(message.formatted(args));
    }
}
