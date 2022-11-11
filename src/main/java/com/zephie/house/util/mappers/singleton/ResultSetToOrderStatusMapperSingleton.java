package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToOrderStatusMapper;

public class ResultSetToOrderStatusMapperSingleton {
    private static volatile ResultSetToOrderStatusMapperSingleton instance;

    private final ResultSetToOrderStatusMapper orderStatusMapper;

    private ResultSetToOrderStatusMapperSingleton() {
        orderStatusMapper = new ResultSetToOrderStatusMapper();
    }

    public static ResultSetToOrderStatusMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToOrderStatusMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToOrderStatusMapperSingleton();
                }
            }
        }
        return instance.orderStatusMapper;
    }
}