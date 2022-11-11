package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToStageMapper;

public class ResultSetToStageMapperSingleton {
    private static volatile ResultSetToStageMapperSingleton instance;

    private final ResultSetToStageMapper stageMapper;

    private ResultSetToStageMapperSingleton() {
        stageMapper = new ResultSetToStageMapper();
    }

    public static ResultSetToStageMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToStageMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToStageMapperSingleton();
                }
            }
        }
        return instance.stageMapper;
    }
}
