package org.example.service.api;

import org.example.core.dto.CurrencyDTO;

import java.util.List;

public interface ISingleCurrencyRateService {

    List<CurrencyDTO> get(String currencyType);

}
