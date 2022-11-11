package com.zephie.house.services.singleton;

import com.zephie.house.services.api.IMenuService;
import com.zephie.house.services.entity.MenuService;
import com.zephie.house.storage.singleton.MenuStorageSingleton;
import com.zephie.house.util.validators.singleton.BasicMenuValidatorSingleton;

public class MenuServiceSingleton {
    private static volatile MenuServiceSingleton instance;

    private final IMenuService menuService;

    private MenuServiceSingleton() {
        menuService = new MenuService(MenuStorageSingleton.getInstance(), BasicMenuValidatorSingleton.getInstance());
    }

    public static IMenuService getInstance() {
        if (instance == null) {
            synchronized (MenuServiceSingleton.class) {
                if (instance == null) {
                    instance = new MenuServiceSingleton();
                }
            }
        }

        return instance.menuService;
    }
}
