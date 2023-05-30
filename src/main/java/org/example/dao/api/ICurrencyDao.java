package org.example.dao.api;

import org.example.core.dto.CurrencyDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICurrencyDao {

    boolean addCurrencyType(String curType);

    List<CurrencyDTO> addCurrencies(String currType, List<CurrencyDTO> currencyDTOsToAd);

    List<CurrencyDTO> getAllCurrencies(String currType);

    List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop);

    List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop);



}
