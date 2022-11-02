package com.zephie.house.services.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.dto.SelectedItemDTO;
import com.zephie.house.services.api.ISelectedItemService;
import com.zephie.house.storage.api.ISelectedItemStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class SelectedItemService implements ISelectedItemService {
    private final ISelectedItemStorage selectedItemStorage;

    public SelectedItemService(ISelectedItemStorage selectedItemStorage) {
        this.selectedItemStorage = selectedItemStorage;
    }

    @Override
    public ISelectedItem create(SelectedItemDTO selectedItemDTO) {
        return null;
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
        return null;
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {

    }
}
