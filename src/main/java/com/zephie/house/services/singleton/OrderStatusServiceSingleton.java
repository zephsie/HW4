package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IOrderStatusService;
import com.zephie.house.services.entity.OrderStatusService;
import com.zephie.house.storage.singleton.OrderStatusStorageSingleton;
import com.zephie.house.storage.singleton.StageStorageSingleton;
import com.zephie.house.storage.singleton.TicketStorageSingleton;

public class OrderStatusServiceSingleton {
    private static volatile OrderStatusServiceSingleton instance;

    private final IOrderStatusService orderStatusService;

    private OrderStatusServiceSingleton() {
        orderStatusService = new OrderStatusService(OrderStatusStorageSingleton.getInstance(), TicketStorageSingleton.getInstance(), StageStorageSingleton.getInstance());
    }

    public static IOrderStatusService getInstance() {
        if (instance == null) {
            synchronized (OrderStatusServiceSingleton.class) {
                if (instance == null) {
                    instance = new OrderStatusServiceSingleton();
                }
            }
        }

        return instance.orderStatusService;
    }
}