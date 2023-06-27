package com.techbank.cqrs.core.exceptions;

public class ConcurrencyException extends RuntimeException{
    public ConcurrencyException(String message) {
        super(message);
    }

    public ConcurrencyException() {
    }
}
