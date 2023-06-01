package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.api.ICurrencyDao;
import org.example.dao.db.factory.CurrencyDbDaoFactory;
import org.example.service.api.ICurrencyService;
import org.example.service.api.ISingleCurrencyRateService;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.util.List;

public class SingleCurrencyRateService implements ISingleCurrencyRateService {


    private ICurrencyService currencyService;

    public SingleCurrencyRateService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public List <CurrencyDTO> get(String currencyType) {
        ValidationCurrencyServiceFactory.getInstance().validateTypeCurrency(currencyType);
        List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies(currencyType);
        return allCurrencies;
    }
}
