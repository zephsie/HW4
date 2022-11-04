package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IOrderService;
import com.zephie.house.services.entity.OrderService;
import com.zephie.house.storage.singleton.MenuRowStorageSingleton;
import com.zephie.house.storage.singleton.OrderStorageSingleton;

public class OrderServiceSingleton {
    private static volatile OrderServiceSingleton instance;

    private final IOrderService service;

    private OrderServiceSingleton() {
        service = new OrderService(OrderStorageSingleton.getInstance(), MenuRowStorageSingleton.getInstance());
    }

    public static IOrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceSingleton.class) {
                if (instance == null) {
                    instance = new OrderServiceSingleton();
                }
            }
        }
        return instance.service;
    }
}
