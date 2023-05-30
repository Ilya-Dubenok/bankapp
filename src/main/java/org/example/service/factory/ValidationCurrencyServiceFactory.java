package org.example.service.factory;

import org.example.service.ValidationCurrencyService;
import org.example.service.api.IValidationCurrencyService;

public class ValidationCurrencyServiceFactory {

    private static IValidationCurrencyService instance;

    private ValidationCurrencyServiceFactory() {

    }

    public static IValidationCurrencyService getInstance() {
        if (instance == null) {
            synchronized (ValidationCurrencyServiceFactory.class) {
                if (instance == null) {
                    instance = new ValidationCurrencyService();
                }
            }
        }
        return instance;
    }
}
