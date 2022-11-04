package com.zephie.house.services.singleton;

import com.zephie.house.services.entity.SelectedItemService;
import com.zephie.house.storage.singleton.SelectedItemStorageSingleton;

public class SelectedItemServiceSingleton {
    private static volatile SelectedItemServiceSingleton instance;

    private final SelectedItemService selectedItemService;

    private SelectedItemServiceSingleton() {
        selectedItemService = new SelectedItemService(SelectedItemStorageSingleton.getInstance());
    }

    public static SelectedItemService getInstance() {
        if (instance == null) {
            synchronized (SelectedItemServiceSingleton.class) {
                if (instance == null) {
                    instance = new SelectedItemServiceSingleton();
                }
            }
        }

        return instance.selectedItemService;
    }
}