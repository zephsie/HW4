package com.zephie.house.util.mappers.singleton;

import com.zephie.house.util.mappers.entity.ResultSetToStageStatusMapper;

public class ResultSetToStageStatusMapperSingleton {
    private static volatile ResultSetToStageStatusMapperSingleton instance;

    private final ResultSetToStageStatusMapper stageStatusMapper;

    private ResultSetToStageStatusMapperSingleton() {
        stageStatusMapper = new ResultSetToStageStatusMapper(ResultSetToStageMapperSingleton.getInstance());
    }

    public static ResultSetToStageStatusMapper getInstance() {
        if (instance == null) {
            synchronized (ResultSetToStageStatusMapperSingleton.class) {
                if (instance == null) {
                    instance = new ResultSetToStageStatusMapperSingleton();
                }
            }
        }
        return instance.stageStatusMapper;
    }
}
