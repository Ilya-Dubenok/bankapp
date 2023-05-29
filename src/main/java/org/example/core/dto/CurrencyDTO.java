package org.example.core.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyDTO {

    private String name;
    private BigDecimal rate;
    private LocalDate date;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String name, BigDecimal rate, LocalDate date) {
        this.name = name;
        this.rate = rate;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
