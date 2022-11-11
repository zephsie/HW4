package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToPizzaMapper;

public class ResultSetToPizzaMapperSingleton {
    private static volatile ResultSetToPizzaMapperSingleton instance;

    private final ResultSetToPizzaMapper pizzaMapper;

    private ResultSetToPizzaMapperSingleton() {
        pizzaMapper = new ResultSetToPizzaMapper();
    }

    public static ResultSetToPizzaMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToPizzaMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToPizzaMapperSingleton();
                }
            }
        }
        return instance.pizzaMapper;
    }
}
