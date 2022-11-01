package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.storage.entity.PizzaInfoStorage;
import com.zephie.house.util.DataSourceInitializer;

public class PizzaInfoStorageSingleton {
    private static volatile PizzaInfoStorageSingleton instance;
    private final IPizzaInfoStorage pizzaInfoStorage;

    private PizzaInfoStorageSingleton() {
        pizzaInfoStorage = new PizzaInfoStorage(DataSourceInitializer.getDataSource());
    }

    public static IPizzaInfoStorage getInstance() {
        if (instance == null) {
            synchronized (PizzaInfoStorageSingleton.class) {
                if (instance == null) {
                    instance = new PizzaInfoStorageSingleton();
                }
            }
        }
        return instance.pizzaInfoStorage;
    }
}