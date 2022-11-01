package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IMenuRowService;
import com.zephie.house.services.entity.MenuRowService;
import com.zephie.house.storage.singleton.MenuRowStorageSingleton;
import com.zephie.house.storage.singleton.MenuStorageSingleton;
import com.zephie.house.storage.singleton.PizzaInfoStorageSingleton;

public class MenuRowServiceSingleton {
    private static volatile MenuRowServiceSingleton instance;

    private final IMenuRowService menuRowService;

    private MenuRowServiceSingleton() {
        menuRowService = new MenuRowService(MenuRowStorageSingleton.getInstance(), PizzaInfoStorageSingleton.getInstance(), MenuStorageSingleton.getInstance());
    }

    public static IMenuRowService getInstance() {
        if (instance == null) {
            synchronized (MenuRowServiceSingleton.class) {
                if (instance == null) {
                    instance = new MenuRowServiceSingleton();
                }
            }
        }
        return instance.menuRowService;
    }
}