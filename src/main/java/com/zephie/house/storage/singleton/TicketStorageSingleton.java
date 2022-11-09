package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.ITicketStorage;
import com.zephie.house.storage.entity.TicketStorage;
import com.zephie.house.util.db.DataSourceInitializer;

public class TicketStorageSingleton {
    private static volatile TicketStorageSingleton instance;

    private final ITicketStorage ticketStorage;

    private TicketStorageSingleton() {
        this.ticketStorage = new TicketStorage(DataSourceInitializer.getDataSource());
    }

    public static ITicketStorage getInstance() {
        if (instance == null) {
            synchronized (TicketStorageSingleton.class) {
                if (instance == null) {
                    instance = new TicketStorageSingleton();
                }
            }
        }

        return instance.ticketStorage;
    }
}
