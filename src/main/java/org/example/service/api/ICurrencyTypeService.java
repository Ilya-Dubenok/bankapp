package org.example.service.api;

import org.example.core.dto.CurrencyTypeDTO;

import java.util.List;

public interface ICurrencyTypeService {

    CurrencyTypeDTO saveCurrencyType(CurrencyTypeDTO curType);

    List<CurrencyTypeDTO> saveCurrencyTypes(List<CurrencyTypeDTO> curTypesList);

    boolean currencyExists(CurrencyTypeDTO curType);

    CurrencyTypeDTO getCurrencyType(String name);

    CurrencyTypeDTO getCurrencyType(long id);

    CurrencyTypeDTO getCurrencyType(long id, String name);

    List<CurrencyTypeDTO> get();

}
