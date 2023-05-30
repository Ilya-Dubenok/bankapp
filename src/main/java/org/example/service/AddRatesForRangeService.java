package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.factory.NBRBServiceFactory;

import java.util.List;


public class AddRatesForRangeService implements IAddRatesForRangeService {
    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange) {
        return NBRBServiceFactory.getInstance().getCurrency(rateRange);
    }
}
