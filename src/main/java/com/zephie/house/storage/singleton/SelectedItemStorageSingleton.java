package com.zephie.house.storage.singleton;

import com.zephie.house.storage.entity.SelectedItemStorage;
import com.zephie.house.util.db.DataSourceInitializer;

public class SelectedItemStorageSingleton {
    private static volatile SelectedItemStorageSingleton instance;

    private final SelectedItemStorage selectedItemStorage;

    private SelectedItemStorageSingleton() {
        selectedItemStorage = new SelectedItemStorage(DataSourceInitializer.getDataSource());
    }

    public static SelectedItemStorage getInstance() {
        if (instance == null) {
            synchronized (SelectedItemStorageSingleton.class) {
                if (instance == null) {
                    instance = new SelectedItemStorageSingleton();
                }
            }
        }
        return instance.selectedItemStorage;
    }
}