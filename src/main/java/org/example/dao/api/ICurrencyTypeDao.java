package org.example.dao.api;

import org.example.core.dto.CurrencyTypeDTO;

import java.util.List;

public interface ICurrencyTypeDao {

    CurrencyTypeDTO saveCurrencyType(CurrencyTypeDTO curType);

    List<CurrencyTypeDTO> saveCurrencyTypes(List<CurrencyTypeDTO> curTypesList);

    boolean currencyExists(CurrencyTypeDTO curType);

    CurrencyTypeDTO getCurrencyType(String name);

    CurrencyTypeDTO getCurrencyType(int id);

    CurrencyTypeDTO getCurrencyType(String name, int id);


}
