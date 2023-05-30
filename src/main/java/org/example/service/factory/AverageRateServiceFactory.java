package org.example.service.factory;

import org.example.service.AverageRateService;
import org.example.service.api.IAverageRateService;

public class AverageRateServiceFactory {

    private static volatile IAverageRateService instance;

    private AverageRateServiceFactory() {
    }

    public static IAverageRateService getInstance() {
        if (instance == null) {
            synchronized (AverageRateServiceFactory.class) {
                if (instance == null) {
                    instance = new AverageRateService();
                }
            }
        }
        return instance;
    }
}
