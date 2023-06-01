package org.example.service.api;

import org.example.core.dto.CurrencyDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICurrencyService {

    List<CurrencyDTO> saveCurrencies(String currType, List<CurrencyDTO> currencyDTOsToAd);

    List<CurrencyDTO> getAllCurrencies(String currType);

    List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop);

    List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop);

}
