package com.example.ecommerce.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String string) {
        super(string);
    }
}
