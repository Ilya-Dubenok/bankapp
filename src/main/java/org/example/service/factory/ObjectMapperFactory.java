package org.example.service.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;

public class ObjectMapperFactory {
    private static volatile ObjectMapper instance;

    public static ObjectMapper getInstance() {
        if (instance == null) {
            synchronized (ObjectMapperFactory.class){
                if (instance == null){
                    instance = new ObjectMapper()
                            .findAndRegisterModules()
                            .setDateFormat(DateFormat.getDateInstance())
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                }
            }
        }
        return instance;
    }
}
