package org.example.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyDTO {

    @JsonProperty("Cur_Abbreviation")
    private String name;
    @JsonProperty("Cur_OfficialRate")
    private BigDecimal rate;

    @JsonProperty("Date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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

    @JsonSetter("Cur_Abbreviation")
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @JsonSetter("Cur_OfficialRate")
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonSetter("Date")
    public void setDate(LocalDate date) {
        this.date = date;
    }

}