package org.example.service.api;

import java.time.LocalDate;

public interface IValidationService {
    void validateDate(String startDate);

    void validateTypeCurrency(String typeCurrency);

    boolean hasRatesForPeriod(String typeCurrency, LocalDate startDate, LocalDate endDate);

}
