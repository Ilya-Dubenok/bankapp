package org.example.service.factory;

import org.example.dao.db.factory.CurrencyTypeDbDaoFactory;
import org.example.service.CurrencyTypeService;
import org.example.service.api.ICurrencyTypeService;

public class CurrencyTypeServiceFactory {

    private static volatile ICurrencyTypeService instance;

    private CurrencyTypeServiceFactory() {
    }

    public static ICurrencyTypeService getInstance() {

        if (instance == null) {
            synchronized (CurrencyTypeServiceFactory.class) {
                if (instance == null) {
                    instance = new CurrencyTypeService(CurrencyTypeDbDaoFactory.getInstance());
                }
            }
        }

        return instance;
    }
}
