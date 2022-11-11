package com.zephie.house.services.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.core.dto.SystemMenuRowDTO;
import com.zephie.house.services.api.IMenuRowService;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.api.IMenuStorage;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.api.IValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MenuRowService implements IMenuRowService {

    private final IMenuRowStorage menuRowStorage;

    private final IPizzaInfoStorage pizzaInfoStorage;

    private final IMenuStorage menuStorage;

    private final IValidator<MenuRowDTO> validator;

    public MenuRowService(IMenuRowStorage menuRowStorage, IPizzaInfoStorage pizzaInfoStorage, IMenuStorage menuStorage, IValidator<MenuRowDTO> validator) {
        this.menuRowStorage = menuRowStorage;
        this.pizzaInfoStorage = pizzaInfoStorage;
        this.menuStorage = menuStorage;
        this.validator = validator;
    }

    @Override
    public IMenuRow create(MenuRowDTO menuRowDTO) {
        validator.validate(menuRowDTO);

        if (!pizzaInfoStorage.isPresent(menuRowDTO.getPizzaInfoId())) {
            throw new FKNotFound("PizzaInfo with id " + menuRowDTO.getPizzaInfoId() + " not found");
        }

        if (!menuStorage.isPresent(menuRowDTO.getMenuId())) {
            throw new FKNotFound("Menu with id " + menuRowDTO.getMenuId() + " not found");
        }

        return menuRowStorage.create(new SystemMenuRowDTO(menuRowDTO.getMenuId(),
                menuRowDTO.getPizzaInfoId(),
                menuRowDTO.getPrice(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<IMenuRow> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return menuRowStorage.read(id);
    }

    @Override
    public Collection<IMenuRow> read() {
        return menuRowStorage.read();
    }

    @Override
    public IMenuRow update(Long id, MenuRowDTO menuRowDTO, LocalDateTime dateUpdate) {
        validator.validate(menuRowDTO);

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("DateUpdate cannot be null");
        }

        Optional<IMenuRow> menuRow = menuRowStorage.read(id);

        if (menuRow.isEmpty()) {
            throw new NotFoundException("MenuRow with id " + id + " not found");
        }

        IMenuRow menuRowToUpdate = menuRow.get();

        if (!menuRowToUpdate.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("MenuRow with id " + id + " has been updated");
        }

        if (!pizzaInfoStorage.isPresent(menuRowDTO.getPizzaInfoId())) {
            throw new FKNotFound("PizzaInfo with id " + menuRowDTO.getPizzaInfoId() + " not found");
        }

        if (!menuStorage.isPresent(menuRowDTO.getMenuId())) {
            throw new FKNotFound("Menu with id " + menuRowDTO.getMenuId() + " not found");
        }

        return menuRowStorage.update(id, new SystemMenuRowDTO(menuRowDTO.getMenuId(),
                        menuRowDTO.getPizzaInfoId(),
                        menuRowDTO.getPrice(),
                        menuRowToUpdate.getCreateDate(),
                        LocalDateTime.now()),
                dateUpdate);
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("DateUpdate cannot be null");
        }

        Optional<IMenuRow> menuRow = menuRowStorage.read(id);

        if (menuRow.isEmpty()) {
            throw new NotFoundException("MenuRow with id " + id + " not found");
        }

        IMenuRow menuRowToDelete = menuRow.get();

        if (!menuRowToDelete.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("MenuRow with id " + id + " has been updated");
        }

        menuRowStorage.delete(id, dateUpdate);
    }
}
