package org.example.core.dto;

import java.math.BigDecimal;

public class AverageRateDTO {
    private String curName;
    private int month;
    private BigDecimal avgRate;

    public AverageRateDTO(String curName, int month) {
        this.curName = curName;
        this.month = month;
    }

    public String getCurName() {
        return curName;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(BigDecimal avgRate) {
        this.avgRate = avgRate;
    }
}
