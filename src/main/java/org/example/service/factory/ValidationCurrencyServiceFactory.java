package org.example.service.factory;

import org.example.service.ValidationCurrencyService;
import org.example.service.api.IValidationService;

public class ValidationCurrencyServiceFactory {

    private static IValidationService instance;

    private ValidationCurrencyServiceFactory() {

    }

    public static IValidationService getInstance() {
        if (instance == null) {
            synchronized (ValidationCurrencyServiceFactory.class) {
                if (instance == null) {
                    instance = new ValidationCurrencyService(CurrencyServiceFactory.getInstance(), CurrencyTypeServiceFactory.getInstance());
                }
            }
        }
        return instance;
    }
}
