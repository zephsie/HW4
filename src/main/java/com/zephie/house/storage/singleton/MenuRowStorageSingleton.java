package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.entity.MenuRowStorage;
import com.zephie.house.util.db.DataSourceInitializer;

public class MenuRowStorageSingleton {
    private static volatile MenuRowStorageSingleton instance;
    private final IMenuRowStorage menuRowStorage;

    private MenuRowStorageSingleton() {
        menuRowStorage = new MenuRowStorage(DataSourceInitializer.getDataSource());
    }

    public static IMenuRowStorage getInstance() {
        if (instance == null) {
            synchronized (MenuRowStorageSingleton.class) {
                if (instance == null) {
                    instance = new MenuRowStorageSingleton();
                }
            }
        }
        return instance.menuRowStorage;
    }
}
