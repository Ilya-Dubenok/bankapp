package org.example.service.api;

import org.example.core.dto.AverageRateDTO;

public interface IAverageRateService {
    AverageRateDTO get(String currencyName, int month);
}
