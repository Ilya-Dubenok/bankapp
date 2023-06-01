package org.example.core.dto;

import java.math.BigDecimal;

public class AverageRateDTO {
    private final String curName;
    private final int month;
    private BigDecimal avgRate;

    public AverageRateDTO(String curName, int month) {
        this.curName = curName;
        this.month = month;
        avgRate = BigDecimal.ZERO;
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
