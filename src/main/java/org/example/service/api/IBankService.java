package org.example.service.api;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.core.dto.RateRangeDTO;

import java.util.List;

public interface IBankService {
    List<CurrencyDTO> getCurrency(RateRangeDTO dto);

    List<CurrencyTypeDTO> getCurrencyTypes();
}
