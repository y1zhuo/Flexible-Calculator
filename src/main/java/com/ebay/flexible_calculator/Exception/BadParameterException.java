package com.ebay.flexible_calculator.Exception;

public class BadParameterException extends RuntimeException {
    public BadParameterException(){
        super();
    }
    public BadParameterException(String message) {
        super(message);
    }
    public BadParameterException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadParameterException(Throwable cause) {
        super(cause);
    }
}