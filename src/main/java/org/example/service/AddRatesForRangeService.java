package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.IAddRatesForRangeService;
import org.example.service.api.IBankService;
import org.example.service.api.ICurrencyService;
import org.example.service.api.ICurrencyTypeService;
import org.example.service.factory.CurrencyServiceFactory;
import org.example.service.factory.CurrencyTypeServiceFactory;
import org.example.service.factory.NBRBServiceFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


public class AddRatesForRangeService implements IAddRatesForRangeService {
    private static final LocalDate START_PERMISSIBLE_DATE = LocalDate.of(2022, 12, 1);
    private static final LocalDate END_PERMISSIBLE_DATE = LocalDate.of(2023, 5, 31);

    private final ICurrencyService currencyService = CurrencyServiceFactory.getInstance();
    private final ICurrencyTypeService currencyTypeService = CurrencyTypeServiceFactory.getInstance();
    private final IBankService bankService = NBRBServiceFactory.getInstance();

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange) {
        return save(rateRange, false);
    }

    @Override
    public List<CurrencyDTO> save(RateRangeDTO rateRange, Boolean showOnlyNew){

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
            currencies = bankService.getCurrency(rateRange);
            if (showOnlyNew){
                currencies = currencyService.saveCurrencies(rateRange.getCurrencyName(), currencies);
            } else {
                currencyService.saveCurrencies(rateRange.getCurrencyName(), currencies);
            }
        }
        return currencies;
    }

    private void validate(RateRangeDTO rateRange){
        validateName(rateRange.getCurrencyName());
        validateDates(rateRange.getBeginDate(), rateRange.getEndDate());
    }

    private void validateDates(LocalDate beginDate, LocalDate endDate){
        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начальная дата раньше конечной");
        }

        if (beginDate.isBefore(START_PERMISSIBLE_DATE) || endDate.isAfter(END_PERMISSIBLE_DATE)){
            throw new IllegalArgumentException("Недопустимый период. Доступны данный в период с 01.12.2022 по 31.05.2023");
        }
    }
    private void validateName(String name){
        CurrencyTypeDTO currencyType = this.currencyTypeService.getCurrencyType(name);
        if (currencyType == null) {
            currencyType = bankService.getCurrencyType(name);
            if (currencyType != null){
                currencyTypeService.saveCurrencyType(currencyType);
            } else {
                throw new IllegalArgumentException("Недопустимый тип валюты");
            }
        }
    }

    private boolean hasRatesForPeriod(RateRangeDTO rateRange){
        LocalDate beginDate = rateRange.getBeginDate();
        LocalDate endDate = rateRange.getEndDate();
        String currencyName = rateRange.getCurrencyName();

        List<CurrencyDTO> allCurrencies = this.currencyService.getAllCurrencies(currencyName, beginDate, endDate);
        long between = DAYS.between(beginDate, endDate) + 1;

        return allCurrencies.size() == between;
    }
}