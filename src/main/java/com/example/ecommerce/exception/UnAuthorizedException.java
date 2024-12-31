package com.example.ecommerce.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String string) {
        super(string);
    }
}
