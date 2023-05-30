package org.example.dao.api;

public class CurrencyTypeNotFoundException extends RuntimeException{
    public CurrencyTypeNotFoundException() {

    }

    public CurrencyTypeNotFoundException(String message) {
        super(message);
    }

    public CurrencyTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public CurrencyTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
