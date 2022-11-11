package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToSelectedItemMapper;

public class ResultSetToSelectedItemMapperSingleton {
    private static volatile ResultSetToSelectedItemMapperSingleton instance;

    private final ResultSetToSelectedItemMapper selectedItemMapper;

    private ResultSetToSelectedItemMapperSingleton() {
        selectedItemMapper = new ResultSetToSelectedItemMapper(ResultSetToMenuRowMapperSingleton.getInstance(), ResultSetToOrderMapperSingleton.getInstance());
    }

    public static ResultSetToSelectedItemMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToSelectedItemMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToSelectedItemMapperSingleton();
                }
            }
        }
        return instance.selectedItemMapper;
    }
}
