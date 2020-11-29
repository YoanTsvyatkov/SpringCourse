package com.fmi.recipes.web;

import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.exception.UnexistingEntityException;
import com.fmi.recipes.model.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = ErrorHandlerControllerAdvice.class)
public class ErrorHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidEntityDataException ex){
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getViolations()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexistingEntityException(UnexistingEntityException ex){
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler({JwtException.class, AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }
}
