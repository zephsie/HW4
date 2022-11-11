package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToTicketMapper;

public class ResultSetToTicketMapperSingleton {
    private static volatile ResultSetToTicketMapperSingleton instance;

    private final ResultSetToTicketMapper ticketMapper;

    private ResultSetToTicketMapperSingleton() {
        ticketMapper = new ResultSetToTicketMapper(ResultSetToOrderMapperSingleton.getInstance());
    }

    public static ResultSetToTicketMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToTicketMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToTicketMapperSingleton();
                }
            }
        }
        return instance.ticketMapper;
    }
}
