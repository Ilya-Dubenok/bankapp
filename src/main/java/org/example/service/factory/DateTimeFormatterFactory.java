package org.example.service.factory;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterFactory {
    private static volatile DateTimeFormatter instance;

    public static DateTimeFormatter getInstance() {
        if (instance == null) {
            synchronized (DateTimeFormatterFactory.class){
                if (instance == null){
                    instance = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }
            }
        }
        return instance;
    }
}
