package org.npeonelove.backend.exception.security;

public class MissingAuthHeadersException extends RuntimeException {
    public MissingAuthHeadersException(String message) {
        super(message);
    }
}
