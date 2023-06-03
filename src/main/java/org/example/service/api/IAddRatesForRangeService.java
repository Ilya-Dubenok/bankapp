package org.example.service.api;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;

import java.util.List;

public interface IAddRatesForRangeService {

     List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew);

     List<CurrencyDTO> save(RateRangeDTO rateRange);

}
