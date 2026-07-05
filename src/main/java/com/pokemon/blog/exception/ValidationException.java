package com.pokemon.blog.exception;

/**
 * Exception cho business logic validation.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}