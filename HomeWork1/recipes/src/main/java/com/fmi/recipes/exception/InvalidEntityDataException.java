package com.fmi.recipes.exception;

import java.util.List;

public class InvalidEntityDataException extends RuntimeException {

    private List<String> violations;

    public InvalidEntityDataException() {
        super();
    }

    public InvalidEntityDataException(String message) {
        super(message);
    }

    public InvalidEntityDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }


    public InvalidEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityDataException(Throwable cause) {
        super(cause);
    }

    public List<String> getViolations() {
        return violations;
    }
}
