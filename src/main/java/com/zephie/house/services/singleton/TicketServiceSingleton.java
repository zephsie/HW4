package com.zephie.house.services.singleton;

import com.zephie.house.services.api.ITicketService;
import com.zephie.house.services.entity.TicketService;
import com.zephie.house.storage.singleton.OrderStorageSingleton;
import com.zephie.house.storage.singleton.TicketStorageSingleton;
import com.zephie.house.util.validators.singleton.BasicTicketValidatorSingleton;

public class TicketServiceSingleton {
    private static volatile TicketServiceSingleton instance;

    private final ITicketService ticketService;

    private TicketServiceSingleton() {
        ticketService = new TicketService(TicketStorageSingleton.getInstance(), OrderStorageSingleton.getInstance(), BasicTicketValidatorSingleton.getInstance());
    }

    public static ITicketService getInstance() {
        if (instance == null) {
            synchronized (TicketServiceSingleton.class) {
                if (instance == null) {
                    instance = new TicketServiceSingleton();
                }
            }
        }
        return instance.ticketService;
    }
}
