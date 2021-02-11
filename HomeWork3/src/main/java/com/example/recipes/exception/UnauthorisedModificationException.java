package com.example.recipes.exception;

public class UnauthorisedModificationException extends RuntimeException {
    public UnauthorisedModificationException(String msg) {
        super(msg);
    }

    public UnauthorisedModificationException() {
        super();
    }

    public UnauthorisedModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedModificationException(Throwable cause) {
        super(cause);
    }
}
