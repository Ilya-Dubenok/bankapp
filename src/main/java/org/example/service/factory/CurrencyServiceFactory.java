package org.example.service.factory;

import org.example.dao.db.factory.CurrencyDbDaoFactory;
import org.example.service.CurrencyService;
import org.example.service.api.ICurrencyService;

public class CurrencyServiceFactory {

    private static volatile ICurrencyService instance;

    private CurrencyServiceFactory(){

    }

    public static ICurrencyService getInstance(){

        if(instance == null){
            synchronized (CurrencyServiceFactory.class){
                if(instance == null){
                    instance = new CurrencyService(CurrencyDbDaoFactory.getInstance());
                }
            }
        }
        return instance;
    }
}
