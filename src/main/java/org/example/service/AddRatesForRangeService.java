package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.api.IValidationService;
import org.example.service.factory.NBRBServiceFactory;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.util.ArrayList;
import java.util.List;


public class AddRatesForRangeService implements IAddRatesForRangeService {

    IValidationService validationService = ValidationCurrencyServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew) {

        List<CurrencyDTO> currencies;

        if (hasRatesForPeriod(rateRange)) {
            if (showOnlyNew){
                currencies = new ArrayList<>();
            } else {
                //TODO Возвращать данные из БД
                currencies = NBRBServiceFactory.getInstance().getCurrency(rateRange);
            }
        } else {
            if (showOnlyNew){
                //TODO Сохранение в базу, в currencies список из базы
                currencies = NBRBServiceFactory.getInstance().getCurrency(rateRange);
            } else {
                //TODO Сохранение в базу, в currencies список всех из NBRBService
                currencies = NBRBServiceFactory.getInstance().getCurrency(rateRange);
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

        //TODO изменить после правки validateDate()
        validationService.validateDate(rateRange.getBeginDate().toString());
        validationService.validateDate(rateRange.getEndDate().toString());
        //TODO
    }

    private boolean hasRatesForPeriod(RateRangeDTO rateRange){
        return validationService.hasRatesForPeriod(rateRange.getCurrencyName(),
                rateRange.getBeginDate(),
                rateRange.getEndDate());
    }
}
