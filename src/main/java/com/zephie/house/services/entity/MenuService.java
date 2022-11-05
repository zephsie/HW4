package com.zephie.house.services.entity;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.dto.MenuDTO;
import com.zephie.house.core.dto.SystemMenuDTO;
import com.zephie.house.services.api.IMenuService;
import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.BasicMenuValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MenuService implements IMenuService {
    private final IMenuStorage menuStorage;

    public MenuService(IMenuStorage menuStorage) {
        this.menuStorage = menuStorage;
    }

    @Override
    public IMenu create(MenuDTO menuDTO) {
        BasicMenuValidator.validate(menuDTO);

        if (menuStorage.read(menuDTO.getName()).isPresent()) {
            throw new NotUniqueException("Menu with name " + menuDTO.getName() + " already exists");
        }

        return menuStorage.create(new SystemMenuDTO(menuDTO.getName(),
                menuDTO.getActive(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<IMenu> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return menuStorage.read(id);
    }

    @Override
    public Collection<IMenu> read() {
        return menuStorage.read();
    }

    @Override
    public IMenu update(Long id, MenuDTO menuDTO, LocalDateTime dateUpdate) {
        BasicMenuValidator.validate(menuDTO);

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<IMenu> menu = menuStorage.read(id);

        if (menu.isEmpty()) {
            throw new NotFoundException("Menu with id " + id + " not found");
        }

        Optional<IMenu> menuByName = menuStorage.read(menuDTO.getName());

        if (menuByName.isPresent() && !menuByName.get().getId().equals(id)) {
            throw new NotUniqueException("Menu with name " + menuDTO.getName() + " already exists");
        }

        IMenu menuToUpdate = menu.get();

        if (!menuToUpdate.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Menu with id " + id + " has been updated");
        }

        return menuStorage.update(id, new SystemMenuDTO(menuDTO.getName(),
                        menuDTO.getActive(),
                        menuToUpdate.getCreateDate(),
                        LocalDateTime.now()),
                dateUpdate);
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<IMenu> menu = menuStorage.read(id);

        if (menu.isEmpty()) {
            throw new NotFoundException("Menu with id " + id + " not found");
        }

        IMenu menuToUpdate = menu.get();

        if (!menuToUpdate.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Menu with id " + id + " has been updated");
        }

        menuStorage.delete(id, dateUpdate);
    }
}