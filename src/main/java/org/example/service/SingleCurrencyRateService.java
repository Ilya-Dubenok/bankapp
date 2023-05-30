package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.service.api.ISingleCurrencyRateService;

import java.util.List;

public class SingleCurrencyRateService implements ISingleCurrencyRateService {
    @Override
    public List<CurrencyDTO> get(String currencyType) {
        return null;
    }
}
