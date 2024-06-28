package com.baas.demo.exception;

public class InvalidAccountOrPasswordException extends RuntimeException {
    public InvalidAccountOrPasswordException(String message) {
        super(message);
    }

}
