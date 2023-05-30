package org.example.service.factory;

import org.example.service.AddRatesForRangeService;
import org.example.service.api.IAddRatesForRangeService;

public class AddRatesForRangeServiceFactory {
    private static volatile IAddRatesForRangeService instance;

    public static IAddRatesForRangeService getInstance() {
        if (instance == null) {
            synchronized (AddRatesForRangeServiceFactory.class) {
                if (instance == null) {
                    instance = new AddRatesForRangeService();
                }
            }
        }
        return instance;
    }
}
