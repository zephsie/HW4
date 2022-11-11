package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.storage.entity.PizzaStorage;
import com.zephie.house.util.db.DataSourceInitializer;
import com.zephie.house.util.mappers.singleton.ResultSetToPizzaMapperSingleton;

public class PizzaStorageSingleton {
    private static volatile PizzaStorageSingleton instance;
    private final IPizzaStorage pizzaStorage;

    private PizzaStorageSingleton() {
        pizzaStorage = new PizzaStorage(DataSourceInitializer.getDataSource(), ResultSetToPizzaMapperSingleton.getInstance());
    }

    public static IPizzaStorage getInstance() {
        if (instance == null) {
            synchronized (PizzaStorageSingleton.class) {
                if (instance == null) {
                    instance = new PizzaStorageSingleton();
                }
            }
        }
        return instance.pizzaStorage;
    }
}
