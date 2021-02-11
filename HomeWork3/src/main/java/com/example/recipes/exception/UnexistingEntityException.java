package com.example.recipes.exception;

public class UnexistingEntityException extends RuntimeException {

    public UnexistingEntityException() {
    }

    public UnexistingEntityException(String message) {
        super(message);
    }

    public UnexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexistingEntityException(Throwable cause) {
        super(cause);
    }
}
