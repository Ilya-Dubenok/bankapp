package org.example.service.api;

import org.example.core.dto.RateRangeDTO;

import java.time.LocalDate;

public interface IValidationService {
    void validateDate(String date);

    void validateDates(LocalDate startDate, LocalDate endDate);

    void validateTypeCurrency(String typeCurrency);

    boolean hasRatesForPeriod(RateRangeDTO dto);

}
