package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.api.IValidationService;
import org.example.service.factory.NBRBServiceFactory;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.util.List;


public class AddRatesForRangeService implements IAddRatesForRangeService {

    IValidationService validationService = ValidationCurrencyServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew) {
        validationService.validateTypeCurrency(rateRange.getCurrencyName());

        //TODO изменить после правки validateDate()
        validationService.validateDate(rateRange.getBeginDate().toString());
        validationService.validateDate(rateRange.getEndDate().toString());
        //TODO

        if (validationService.hasRatesForPeriod(rateRange.getCurrencyName(),
                rateRange.getBeginDate(),
                rateRange.getEndDate()))
        {
            //TODO Возвращать данные из БД
        }

        return NBRBServiceFactory.getInstance().getCurrency(rateRange);
    }

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange) {
        return save(rateRange, false);
    }
}
