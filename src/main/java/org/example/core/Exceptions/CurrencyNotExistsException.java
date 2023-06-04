package org.example.core.Exceptions;

public class CurrencyNotExistsException extends RuntimeException{
    public CurrencyNotExistsException() {
    }

    public CurrencyNotExistsException(String message) {
        super(message);
    }

    public CurrencyNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotExistsException(Throwable cause) {
        super(cause);
    }

    public CurrencyNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
