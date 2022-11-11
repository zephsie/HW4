package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToMenuRowMapper;

public class ResultSetToMenuRowMapperSingleton {
    private static volatile ResultSetToMenuRowMapperSingleton instance;

    private final ResultSetToMenuRowMapper pizzaInfoMapper;

    private ResultSetToMenuRowMapperSingleton() {
        pizzaInfoMapper = new ResultSetToMenuRowMapper(ResultSetToPizzaInfoMapperSingleton.getInstance(), ResultSetToMenuMapperSingleton.getInstance());
    }

    public static ResultSetToMenuRowMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToMenuRowMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToMenuRowMapperSingleton();
                }
            }
        }
        return instance.pizzaInfoMapper;
    }
}
