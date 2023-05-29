package org.example.service.factory;

import org.example.service.CurrencyNBRBService;

public class CurrencyNBRBServiceFactory {
    private static volatile CurrencyNBRBService instance;

    public static CurrencyNBRBService getInstance() {
        if (instance == null) {
            synchronized (CurrencyNBRBServiceFactory.class){
                if (instance == null){
                    instance = new CurrencyNBRBService();
                }
            }
        }
        return instance;
    }
}
