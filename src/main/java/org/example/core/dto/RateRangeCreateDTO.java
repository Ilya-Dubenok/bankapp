package org.example.core.dto;

import java.time.LocalDate;

public class RateRangeCreateDTO {
    private final String currencyName;
    private final String beginDate;
    private final String endDate;

    public RateRangeCreateDTO(String currencyName, String beginDate, String endDate) {
        this.currencyName = currencyName;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
