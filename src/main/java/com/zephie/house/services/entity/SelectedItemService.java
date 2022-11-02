package com.zephie.house.services.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.dto.SelectedItemDTO;
import com.zephie.house.core.dto.SystemSelectedItemDTO;
import com.zephie.house.services.api.ISelectedItemService;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.api.ISelectedItemStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.SelectedItemValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class SelectedItemService implements ISelectedItemService {
    private final ISelectedItemStorage selectedItemStorage;

    private final IMenuRowStorage menuRowStorage;

    public SelectedItemService(ISelectedItemStorage selectedItemStorage, IMenuRowStorage menuRowStorage) {
        this.selectedItemStorage = selectedItemStorage;
        this.menuRowStorage = menuRowStorage;
    }

    @Override
    public ISelectedItem create(SelectedItemDTO selectedItemDTO) {
        SelectedItemValidator.validate(selectedItemDTO);

        if (menuRowStorage.read(selectedItemDTO.getMenuRowId()).isEmpty()) {
            throw new FKNotFound("Menu row with id " + selectedItemDTO.getMenuRowId() + " not found");
        }

        return selectedItemStorage.create(new SystemSelectedItemDTO(selectedItemDTO.getMenuRowId(),
                selectedItemDTO.getCount(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<ISelectedItem> read(Long id) {
        return selectedItemStorage.read(id);
    }

    @Override
    public Collection<ISelectedItem> read() {
        return selectedItemStorage.read();
    }

    @Override
    public ISelectedItem update(Long id, SelectedItemDTO selectedItemDTO, LocalDateTime dateUpdate) {
        SelectedItemValidator.validate(selectedItemDTO);

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<ISelectedItem> selectedItem = selectedItemStorage.read(id);

        if (selectedItem.isEmpty()) {
            throw new NotFoundException("Selected item with id " + id + " not found");
        }

        ISelectedItem storageSelectedItem = selectedItem.get();

        if (!storageSelectedItem.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Selected item with id " + id + " has been updated");
        }

        if (menuRowStorage.read(selectedItemDTO.getMenuRowId()).isEmpty()) {
            throw new FKNotFound("MenuRow with id " + selectedItemDTO.getMenuRowId() + " not found");
        }

        return selectedItemStorage.update(id, new SystemSelectedItemDTO(selectedItemDTO.getMenuRowId(),
                        selectedItemDTO.getCount(),
                        storageSelectedItem.getCreateDate(),
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

        Optional<ISelectedItem> selectedItem = selectedItemStorage.read(id);

        if (selectedItem.isEmpty()) {
            throw new NotFoundException("Selected item with id " + id + " not found");
        }

        ISelectedItem storageSelectedItem = selectedItem.get();

        if (!storageSelectedItem.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Selected item with id " + id + " has been updated");
        }

        selectedItemStorage.delete(id, dateUpdate);
    }
}