package com.example.Gatekeeper_backend.Exceptions;

public class BadRequest extends RuntimeException{
    public BadRequest(String message) {
        super(message);
    }
}
