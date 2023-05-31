package org.example.dao.api;

import org.example.core.dto.CurrencyDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICurrencyDao {

    /**
     * Сохранаяет курсы в базу и возвращает только уникальные значения, записанные из базы
     * @param currType
     * @param currencyDTOsToAd
     * @return
     */
    List<CurrencyDTO> saveCurrencies(String currType, List<CurrencyDTO> currencyDTOsToAd);

    List<CurrencyDTO> getAllCurrencies(String currType);

    List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop);

    /**
     * Возвращает курсы за заданный период исключая выходная дни
     * @param currType
     * @param start
     * @param stop
     * @return
     */
    List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop);



}
