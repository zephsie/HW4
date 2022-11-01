package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IPizzaInfoService;
import com.zephie.house.services.entity.PizzaInfoService;
import com.zephie.house.storage.singleton.PizzaInfoStorageSingleton;
import com.zephie.house.storage.singleton.PizzaStorageSingleton;

public class PizzaInfoServiceSingleton {
    private static volatile PizzaInfoServiceSingleton instance;

    private final IPizzaInfoService pizzaInfoService;

    private PizzaInfoServiceSingleton() {
        pizzaInfoService = new PizzaInfoService(PizzaInfoStorageSingleton.getInstance(), PizzaStorageSingleton.getInstance());
    }

    public static IPizzaInfoService getInstance() {
        if (instance == null) {
            synchronized (PizzaInfoServiceSingleton.class) {
                if (instance == null) {
                    instance = new PizzaInfoServiceSingleton();
                }
            }
        }

        return instance.pizzaInfoService;
    }
}
