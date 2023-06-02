package org.example.service;

import org.example.core.dto.CurrencyDTO;
import org.example.core.dto.CurrencyTypeDTO;
import org.example.core.dto.RateRangeDTO;
import org.example.service.api.ICurrencyService;
import org.example.service.api.ICurrencyTypeService;
import org.example.service.api.IValidationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class ValidationCurrencyService implements IValidationService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DAY_PATTERN = "[0-2][1-9]|31|30|10|20";
    private static final String MONTH_PATTERN = "0[1-9]|1[0-2]";
    private static final String YEAR_PATTERN = "[1-2]\\d{3}";
    private static final String DAY_MONTH_PATTERN = "2902|3002|3102|3104|3106|3109|3111";
    private static final String DAY_MONTH_LEAP_PATTERN = "3002|3102|3104|3106|3109|3111";
    private static final LocalDate START_PERMISSIBLE_DATE = LocalDate.of(2022, 12, 1);
    private static final LocalDate END_PERMISSIBLE_DATE = LocalDate.of(2023, 5, 31);

    private ICurrencyService currencyService;
    private ICurrencyTypeService currencyTypeService;

    public ValidationCurrencyService(ICurrencyService currencyService, ICurrencyTypeService currencyTypeService) {
        this.currencyService = currencyService;
        this.currencyTypeService = currencyTypeService;
    }


    @Override
    public void validateDate(String date) {
        if (date.length() != 10) {
            throw new IllegalArgumentException("Неверная длина даты");
        }

        String[] dateParts = date.split("-");

        if (dateParts.length != 3) {
            throw new IllegalArgumentException("Неверный даты");
        }

        String day = dateParts[2];
        String month = dateParts[1];
        String year = dateParts[0];

        if (!Pattern.matches(DAY_PATTERN, day)) {
            throw new IllegalArgumentException("Неверный день месяца");
        }

        if (!Pattern.matches(MONTH_PATTERN, month)) {
            throw new IllegalArgumentException("Неверный месяц");
        }

        if (!Pattern.matches(YEAR_PATTERN, year)) {
            throw new IllegalArgumentException("Неверный год");
        }

        if (!validateDayMonth(day, month, year)) {
            throw new IllegalArgumentException("Неверная дата");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate parseDate = LocalDate.parse(date, formatter);
        validateDate(parseDate);

    }

    @Override
    public void validateDate(LocalDate date) {
        if (date.isBefore(START_PERMISSIBLE_DATE) || date.isAfter(END_PERMISSIBLE_DATE)) {
            throw new IllegalArgumentException("Недопустимая дата. Доступны данный в период с 01.12.2022 по 31.05.2023");
        }
    }

    @Override
    public void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начальная дата раньше конечной");
        }
        try {
            validateDate(startDate);
            validateDate(endDate);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Недопустимый период. Доступны данный в период с 01.12.2022 по 31.05.2023");
        }
    }


    @Override
    public void validateTypeCurrency(String typeCurrency) {
        CurrencyTypeDTO currencyType = this.currencyTypeService.getCurrencyType(typeCurrency);
        if (currencyType == null) {
            throw new IllegalArgumentException("Такой валюты не существует");
        }
    }

    @Override
    public boolean hasRatesForPeriod(RateRangeDTO dto) {
        LocalDate beginDate = dto.getBeginDate();
        LocalDate endDate = dto.getEndDate();
        String currencyName = dto.getCurrencyName();
        validateTypeCurrency(currencyName);
        validateDates(beginDate, endDate);
        List<CurrencyDTO> allCurrencies = this.currencyService.getAllCurrencies(currencyName, beginDate, endDate);
        long between = DAYS.between(beginDate, endDate) + 1;

        return allCurrencies.size() == between;
    }

    private boolean validateDayMonth(String day, String month, String year) {
        String dayMonth = day + month;
        if (isLeapYear(year)) {
            return !Pattern.matches(DAY_MONTH_LEAP_PATTERN, dayMonth);
        } else {
            return !Pattern.matches(DAY_MONTH_PATTERN, dayMonth);
        }
    }

    private boolean isLeapYear(String year) {
        int yearInt = Integer.parseInt(year);

        if (yearInt % 4 == 0) {
            if (yearInt % 100 == 0) {
                if (!(yearInt % 400 == 0)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
