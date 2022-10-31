package com.zephie.house.services.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.services.api.IMenuRowService;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.entity.MenuRowStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MenuRowService implements IMenuRowService {
    private static volatile MenuRowService instance;

    private final IMenuRowStorage menuRowStorage;

    private MenuRowService() {
        menuRowStorage = MenuRowStorage.getInstance();
    }

    @Override
    public IMenuRow create(MenuRowDTO menuRowDTO) {
        return null;
    }

    @Override
    public Optional<IMenuRow> read(Long id) {
        return menuRowStorage.read(id);
    }

    @Override
    public Collection<IMenuRow> read() {
        return menuRowStorage.read();
    }

    @Override
    public IMenuRow update(Long id, MenuRowDTO menuRowDTO, LocalDateTime dateUpdate) {
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

    }

    public static MenuRowService getInstance() {
        if (instance == null) {
            synchronized (MenuRowService.class) {
                if (instance == null) {
                    instance = new MenuRowService();
                }
            }
        }
        return instance;
    }
}
