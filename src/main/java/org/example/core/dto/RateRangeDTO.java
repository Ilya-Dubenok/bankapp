package org.example.core.dto;

import java.time.LocalDate;

public class RateRangeDTO {
    private final String currencyName;
    private final LocalDate beginDate;
    private final LocalDate endDate;

    public RateRangeDTO(String currencyName, LocalDate beginDate, LocalDate endDate) {
        this.currencyName = currencyName;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
