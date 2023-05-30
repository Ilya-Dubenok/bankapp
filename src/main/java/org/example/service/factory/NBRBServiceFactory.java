package org.example.service.factory;

import org.example.service.NBRBService;

public class NBRBServiceFactory {
    private static volatile NBRBService instance;

    public static NBRBService getInstance() {
        if (instance == null) {
            synchronized (NBRBServiceFactory.class){
                if (instance == null){
                    instance = new NBRBService();
                }
            }
        }
        return instance;
    }
}
