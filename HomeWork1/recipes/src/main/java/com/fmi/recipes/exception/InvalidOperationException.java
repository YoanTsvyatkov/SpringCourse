package com.fmi.recipes.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidOperationException extends AuthenticationException {
    public InvalidOperationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidOperationException(String msg) {
        super(msg);
    }
}
