package org.example.service;

import org.example.service.api.IValidationService;

import java.time.LocalDate;

public class ValidationCurrencyService implements IValidationService {
    @Override
    public void validateDate(String startDate) {

    }

    @Override
    public void validateTypeCurrency(String typeCurrency) {

    }

    @Override
    public boolean hasRatesForPeriod(String typeCurrency, LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
