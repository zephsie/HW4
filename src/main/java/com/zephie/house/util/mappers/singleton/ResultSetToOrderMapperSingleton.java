package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToOrderMapper;

public class ResultSetToOrderMapperSingleton {
    private static volatile ResultSetToOrderMapperSingleton instance;

    private final ResultSetToOrderMapper orderMapper;

    private ResultSetToOrderMapperSingleton() {
        orderMapper = new ResultSetToOrderMapper();
    }

    public static ResultSetToOrderMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToOrderMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToOrderMapperSingleton();
                }
            }
        }
        return instance.orderMapper;
    }
}
