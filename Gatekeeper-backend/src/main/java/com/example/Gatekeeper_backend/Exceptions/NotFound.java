package com.example.Gatekeeper_backend.Exceptions;

public class NotFound extends RuntimeException{
    public NotFound(String message) {
        super(message);
    }
}
