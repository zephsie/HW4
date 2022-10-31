package com.zephie.house.services.entity;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.dto.MenuDTO;
import com.zephie.house.services.api.IMenuService;
import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.storage.entity.MenuStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MenuService implements IMenuService {
    private static volatile MenuService instance;

    private final IMenuStorage menuStorage;

    private MenuService() {
        menuStorage = MenuStorage.getInstance();
    }

    @Override
    public IMenu create(MenuDTO menuDTO) {
        return null;
    }

    @Override
    public Optional<IMenu> read(Long id) {
        return menuStorage.read(id);
    }

    @Override
    public Collection<IMenu> read() {
        return menuStorage.read();
    }

    @Override
    public IMenu update(Long id, MenuDTO menuDTO, LocalDateTime dateUpdate) {
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

    }

    public static MenuService getInstance() {
        if (instance == null) {
            synchronized (MenuService.class) {
                if (instance == null) {
                    instance = new MenuService();
                }
            }
        }
        return instance;
    }
}