package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IOrderStorage;
import com.zephie.house.storage.entity.OrderStorage;
import com.zephie.house.util.db.DataSourceInitializer;

public class OrderStorageSingleton {
    private static volatile OrderStorageSingleton instance;

    private final IOrderStorage orderStorage;

    private OrderStorageSingleton() {
        this.orderStorage = new OrderStorage(DataSourceInitializer.getDataSource());
    }

    public static IOrderStorage getInstance() {
        if (instance == null) {
            synchronized (OrderStorageSingleton.class) {
                if (instance == null) {
                    instance = new OrderStorageSingleton();
                }
            }
        }
        return instance.orderStorage;
    }
}