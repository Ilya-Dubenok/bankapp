package org.example.service;

import org.example.core.Exceptions.CurrencyNotExistsException;
import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.service.api.IBankService;
import org.example.service.api.ICurrencyService;
import org.example.service.api.ICurrencyTypeService;
import org.example.service.api.ISingleCurrencyRateService;

import java.util.List;

public class SingleCurrencyRateService implements ISingleCurrencyRateService {


    private ICurrencyService currencyService;
    private ICurrencyTypeService currencyTypeService;
    private IBankService nbrbService;

    public SingleCurrencyRateService(ICurrencyService currencyService, ICurrencyTypeService currencyTypeService, IBankService nbrbService) {
        this.currencyService = currencyService;
        this.currencyTypeService = currencyTypeService;
    }

    @Override
    public List<CurrencyDTO> get(String currencyType) {
        CurrencyTypeDTO currencyTypeDto = this.currencyTypeService.getCurrencyType(currencyType);

        if (currencyTypeDto == null) {
            try {
                currencyTypeDto = this.nbrbService.getCurrencyType(currencyType);
                this.currencyTypeService.saveCurrencyType(currencyTypeDto);
            } catch (Exception e) {
                String message = "Такой валюты не существует";
                throw new CurrencyNotExistsException(message, e.getCause());
            }

        }

        List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies(currencyType);
        return allCurrencies;
    }

}
