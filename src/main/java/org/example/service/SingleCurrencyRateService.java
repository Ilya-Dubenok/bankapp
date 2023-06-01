package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.api.ICurrencyDao;
import org.example.dao.db.factory.CurrencyDbDaoFactory;
import org.example.service.api.ISingleCurrencyRateService;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.util.List;

public class SingleCurrencyRateService implements ISingleCurrencyRateService {

    //TODO заменить на сервис
    private ICurrencyDao dao;

    public SingleCurrencyRateService(ICurrencyDao dao){
        this.dao = dao;
    }

    @Override
    public List <CurrencyDTO> get(String currencyType) {
        ValidationCurrencyServiceFactory.getInstance().validateTypeCurrency(currencyType);
        List<CurrencyDTO> allCurrencies = dao.getAllCurrencies(currencyType);
        return allCurrencies;
    }
}
