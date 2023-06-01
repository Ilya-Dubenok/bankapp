package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.api.ICurrencyDao;
import org.example.service.api.ICurrencyService;

import java.time.LocalDate;
import java.util.List;

public class CurrencyService implements ICurrencyService {

    private ICurrencyDao currencyDao;

    public CurrencyService(ICurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public List<CurrencyDTO> saveCurrencies(String currType, List<CurrencyDTO> currencyDTOsToAd) {
        return this.currencyDao.saveCurrencies(currType, currencyDTOsToAd);
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType) {
        return this.currencyDao.getAllCurrencies(currType);
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop) {
        return this.currencyDao.getAllCurrencies(currType, start, stop);
    }

    @Override
    public List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop) {
        return this.currencyDao.getAllCurrenciesOnWorkdaysOnly(currType, start, stop);
    }
}
