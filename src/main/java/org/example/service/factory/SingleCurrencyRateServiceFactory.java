package org.example.service.factory;

import org.example.dao.db.factory.CurrencyDbDaoFactory;
import org.example.service.SingleCurrencyRateService;
import org.example.service.api.ISingleCurrencyRateService;

public class SingleCurrencyRateServiceFactory {

    private static ISingleCurrencyRateService instance;

    private SingleCurrencyRateServiceFactory() {
    }

    public static ISingleCurrencyRateService getInstance() {
        if (instance == null) {
            synchronized (SingleCurrencyRateServiceFactory.class) {
                if (instance == null) {
                    instance = new SingleCurrencyRateService(CurrencyDbDaoFactory.getInstance());
                }
            }
        }
        return instance;
    }
}
