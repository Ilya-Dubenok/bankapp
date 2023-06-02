package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.RateRangeCreateDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.api.ICurrencyService;
import org.example.service.api.IValidationService;
import org.example.service.factory.CurrencyServiceFactory;
import org.example.service.factory.NBRBServiceFactory;
import org.example.service.factory.ValidationCurrencyServiceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AddRatesForRangeService implements IAddRatesForRangeService {

    private final IValidationService validationService = ValidationCurrencyServiceFactory.getInstance();
    private final ICurrencyService currencyService = CurrencyServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> save(RateRangeCreateDTO createDTO, Boolean showOnlyNew) {

        RateRangeDTO rateRange = createRateRangeDTO(createDTO);

        return save(rateRange, showOnlyNew);
    }

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange) {
        return save(rateRange, false);
    }

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew){

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

    private RateRangeDTO createRateRangeDTO(RateRangeCreateDTO createDTO){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        validationService.validateTypeCurrency(createDTO.getCurrencyName());

        validationService.validateDate(createDTO.getBeginDate());
        validationService.validateDate(createDTO.getEndDate());

        String name = createDTO.getCurrencyName();
        LocalDate beginDate = LocalDate.parse(createDTO.getBeginDate(), formatter);
        LocalDate endDate = LocalDate.parse(createDTO.getEndDate(), formatter);

        RateRangeDTO dto = new RateRangeDTO(name, beginDate, endDate);

        validationService.validateDates(dto.getBeginDate(), dto.getEndDate());

        return dto;
    }

    private boolean hasRatesForPeriod(RateRangeDTO rateRange){
        return validationService.hasRatesForPeriod(rateRange);
    }

}
