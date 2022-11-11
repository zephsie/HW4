package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IStageStorage;
import com.zephie.house.storage.entity.StageStorage;
import com.zephie.house.util.db.DataSourceInitializer;
import com.zephie.house.util.mappers.singleton.ResultSetToStageMapperSingleton;

public class StageStorageSingleton {
    private static volatile StageStorageSingleton instance;

    private final IStageStorage stageStorage;

    private StageStorageSingleton() {
        this.stageStorage = new StageStorage(DataSourceInitializer.getDataSource(), ResultSetToStageMapperSingleton.getInstance());
    }

    public static IStageStorage getInstance() {
        if (instance == null) {
            synchronized (StageStorageSingleton.class) {
                if (instance == null) {
                    instance = new StageStorageSingleton();
                }
            }
        }
        return instance.stageStorage;
    }
}
