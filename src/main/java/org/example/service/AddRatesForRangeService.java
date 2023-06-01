package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.api.ICurrencyService;
import org.example.service.api.IValidationService;
import org.example.service.factory.CurrencyServiceFactory;
import org.example.service.factory.NBRBServiceFactory;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.util.ArrayList;
import java.util.List;


public class AddRatesForRangeService implements IAddRatesForRangeService {

    IValidationService validationService = ValidationCurrencyServiceFactory.getInstance();
    ICurrencyService currencyService = CurrencyServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew) {

        validate(rateRange);

        List<CurrencyDTO> currencies;

        if (hasRatesForPeriod(rateRange)) {
            if (showOnlyNew){
                currencies = new ArrayList<>();
            } else {
                currencies = currencyService.getAllCurrencies(rateRange.getCurrencyName(),
                        rateRange.getBeginDate(),
                        rateRange.getEndDate());
            }
        } else {
            currencies = NBRBServiceFactory.getInstance().getCurrency(rateRange);
            if (showOnlyNew){
                currencies = currencyService.saveCurrencies(rateRange.getCurrencyName(), currencies);
            } else {
                currencyService.saveCurrencies(rateRange.getCurrencyName(), currencies);
            }
        }
        return currencies;
    }

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange) {
        return save(rateRange, false);
    }

    private void validate(RateRangeDTO rateRange){
        validationService.validateTypeCurrency(rateRange.getCurrencyName());

        validationService.validateDate(rateRange.getBeginDate().toString());
        validationService.validateDate(rateRange.getEndDate().toString());

        validationService.validateDates(rateRange.getBeginDate(), rateRange.getEndDate());
    }

    private boolean hasRatesForPeriod(RateRangeDTO rateRange){
        return validationService.hasRatesForPeriod(rateRange);
    }
}
