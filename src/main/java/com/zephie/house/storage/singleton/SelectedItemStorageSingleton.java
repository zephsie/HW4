package com.zephie.house.storage.singleton;

import com.zephie.house.storage.api.ISelectedItemStorage;
import com.zephie.house.storage.entity.SelectedItemStorage;
import com.zephie.house.util.DataSourceInitializer;

public class SelectedItemStorageSingleton {
    private static volatile SelectedItemStorageSingleton instance;

    private final ISelectedItemStorage selectedItemStorage;

    private SelectedItemStorageSingleton() {
        selectedItemStorage = new SelectedItemStorage(DataSourceInitializer.getDataSource());
    }

    public static ISelectedItemStorage getInstance() {
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