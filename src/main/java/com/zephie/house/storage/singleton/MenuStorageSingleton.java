package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.storage.entity.MenuStorage;
import com.zephie.house.util.DataSourceInitializer;

public class MenuStorageSingleton {
    private static volatile MenuStorageSingleton instance;
    private final IMenuStorage menuStorage;

    private MenuStorageSingleton() {
        menuStorage = new MenuStorage(DataSourceInitializer.getDataSource());
    }

    public static IMenuStorage getInstance() {
        if (instance == null) {
            synchronized (MenuStorageSingleton.class) {
                if (instance == null) {
                    instance = new MenuStorageSingleton();
                }
            }
        }
        return instance.menuStorage;
    }
}
