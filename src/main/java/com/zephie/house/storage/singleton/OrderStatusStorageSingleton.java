package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IOrderStatusStorage;
import com.zephie.house.storage.entity.OrderStatusStorage;
import com.zephie.house.util.db.DataSourceInitializer;

public class OrderStatusStorageSingleton {
    private static volatile OrderStatusStorageSingleton instance;

    private final IOrderStatusStorage orderStatusStorage;

    private OrderStatusStorageSingleton() {
        orderStatusStorage = new OrderStatusStorage(DataSourceInitializer.getDataSource());
    }

    public static IOrderStatusStorage getInstance() {
        if (instance == null) {
            synchronized (OrderStatusStorageSingleton.class) {
                if (instance == null) {
                    instance = new OrderStatusStorageSingleton();
                }
            }
        }
        return instance.orderStatusStorage;
    }
}