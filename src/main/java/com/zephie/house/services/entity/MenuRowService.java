package com.zephie.house.services.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.services.api.IMenuRowService;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.entity.MenuRowStorage;
import com.zephie.house.util.validators.MenuRowValidator;

import java.util.Collection;
import java.util.Optional;

public class MenuRowService implements IMenuRowService {
    private static volatile MenuRowService instance;
    private final IMenuRowStorage storage;

    private MenuRowService() {
        storage = MenuRowStorage.getInstance();
    }

    @Override
    public Optional<IMenuRow> read(Long id) {
        return storage.read(id);
    }

    @Override
    public void delete(Long id) {
        storage.delete(id);
    }

    @Override
    public Collection<IMenuRow> get() {
        return storage.get();
    }

    @Override
    public void create(MenuRowDTO menuRow) {
        MenuRowValidator.validate(menuRow);
        storage.create(menuRow);
    }

    @Override
    public void update(Long id, MenuRowDTO menuRow) {
        MenuRowValidator.validate(menuRow);
        storage.update(id, menuRow);
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
