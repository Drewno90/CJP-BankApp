package com.luxoft.bankapp.handling_exceptions;

public class FeedException extends RuntimeException {
    public FeedException(String message) {
         super(message);
    }
}