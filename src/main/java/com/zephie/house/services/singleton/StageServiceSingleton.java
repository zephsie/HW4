package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IStageService;
import com.zephie.house.services.entity.StageService;
import com.zephie.house.storage.singleton.StageStorageSingleton;

public class StageServiceSingleton {
    private static volatile StageServiceSingleton instance;

    private final IStageService service;

    private StageServiceSingleton() {
        this.service = new StageService(StageStorageSingleton.getInstance());
    }

    public static IStageService getInstance() {
        if (instance == null) {
            synchronized (StageServiceSingleton.class) {
                if (instance == null) {
                    instance = new StageServiceSingleton();
                }
            }
        }

        return instance.service;
    }
}
