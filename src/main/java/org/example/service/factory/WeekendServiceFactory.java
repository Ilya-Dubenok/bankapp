package org.example.service.factory;

import org.example.service.WeekendService;
import org.example.service.api.IWeekendService;

public class WeekendServiceFactory {

    private static volatile IWeekendService instance;

    private WeekendServiceFactory() {
    }

    public static IWeekendService getInstance() {
        if (instance == null) {
            synchronized (WeekendServiceFactory.class) {
                if (instance == null) {
                    instance = new WeekendService();
                }
            }
        }
        return instance;
    }
}
