package org.example.service.factory;

import org.example.service.ValidationService;
import org.example.service.api.IValidationService;

public class ValidationCurrencyServiceFactory {

    private static IValidationService instance;

    private ValidationCurrencyServiceFactory() {

    }

    public static IValidationService getInstance() {
        if (instance == null) {
            synchronized (ValidationCurrencyServiceFactory.class) {
                if (instance == null) {
                    instance = new ValidationService();
                }
            }
        }
        return instance;
    }
}
