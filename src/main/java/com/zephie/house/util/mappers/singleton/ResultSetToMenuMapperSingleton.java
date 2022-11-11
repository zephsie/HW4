package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToMenuMapper;

public class ResultSetToMenuMapperSingleton {
    private static volatile ResultSetToMenuMapperSingleton instance;

    private final ResultSetToMenuMapper pizzaInfoMapper;

    private ResultSetToMenuMapperSingleton() {
        pizzaInfoMapper = new ResultSetToMenuMapper(ResultSetToPizzaInfoMapperSingleton.getInstance());
    }

    public static ResultSetToMenuMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToMenuMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToMenuMapperSingleton();
                }
            }
        }
        return instance.pizzaInfoMapper;
    }
}
