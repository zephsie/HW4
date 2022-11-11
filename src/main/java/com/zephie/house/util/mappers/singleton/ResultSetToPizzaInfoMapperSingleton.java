package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToPizzaInfoMapper;

public class ResultSetToPizzaInfoMapperSingleton {
    private static volatile ResultSetToPizzaInfoMapperSingleton instance;

    private final ResultSetToPizzaInfoMapper pizzaInfoMapper;

    private ResultSetToPizzaInfoMapperSingleton() {
        pizzaInfoMapper = new ResultSetToPizzaInfoMapper(ResultSetToPizzaMapperSingleton.getInstance());
    }

    public static ResultSetToPizzaInfoMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToPizzaInfoMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToPizzaInfoMapperSingleton();
                }
            }
        }
        return instance.pizzaInfoMapper;
    }
}
