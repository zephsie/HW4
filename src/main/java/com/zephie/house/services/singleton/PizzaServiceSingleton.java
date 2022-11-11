package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IPizzaService;
import com.zephie.house.services.entity.PizzaService;
import com.zephie.house.storage.singleton.PizzaStorageSingleton;
import com.zephie.house.util.validators.singleton.BasicPizzaValidatorSingleton;

public class PizzaServiceSingleton {
    private static volatile PizzaServiceSingleton instance;

    private final IPizzaService pizzaService;

    private PizzaServiceSingleton() {
        pizzaService = new PizzaService(PizzaStorageSingleton.getInstance(), BasicPizzaValidatorSingleton.getInstance());
    }

    public static IPizzaService getInstance() {
        if (instance == null) {
            synchronized (PizzaServiceSingleton.class) {
                if (instance == null) {
                    instance = new PizzaServiceSingleton();
                }
            }
        }
        return instance.pizzaService;
    }
}