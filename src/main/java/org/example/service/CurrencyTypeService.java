package org.example.service;

import org.example.core.dto.CurrencyTypeDTO;
import org.example.dao.api.ICurrencyTypeDao;
import org.example.service.api.ICurrencyTypeService;

import java.util.List;

public class CurrencyTypeService implements ICurrencyTypeService {

    private ICurrencyTypeDao currencyTypeDao;

    public CurrencyTypeService(ICurrencyTypeDao currencyTypeDao) {
        this.currencyTypeDao = currencyTypeDao;
    }

    @Override
    public CurrencyTypeDTO saveCurrencyType(CurrencyTypeDTO curType) {
        return this.currencyTypeDao.saveCurrencyType(curType);
    }

    @Override
    public List<CurrencyTypeDTO> saveCurrencyTypes(List<CurrencyTypeDTO> curTypesList) {
        return this.currencyTypeDao.saveCurrencyTypes(curTypesList);
    }

    @Override
    public boolean currencyExists(CurrencyTypeDTO curType) {
        return this.currencyExists(curType);
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(String name) {
        return this.currencyTypeDao.getCurrencyType(name);
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(long id) {
        return this.currencyTypeDao.getCurrencyType(id);
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(long id, String name) {
        return this.currencyTypeDao.getCurrencyType(id, name);
    }

    @Override
    public List<CurrencyTypeDTO> get() {
        return this.currencyTypeDao.get();
    }
}
