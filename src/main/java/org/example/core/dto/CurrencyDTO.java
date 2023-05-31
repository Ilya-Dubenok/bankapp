package org.example.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

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

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("Cur_Abbreviation")
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("rate")
    public BigDecimal getRate() {
        return rate;
    }

    @JsonSetter("Cur_OfficialRate")
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @JsonGetter("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    @JsonSetter("Date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public void setDate(LocalDate date) {
        this.date = date;
    }
}