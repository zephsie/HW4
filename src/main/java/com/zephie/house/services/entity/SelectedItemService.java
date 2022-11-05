package com.zephie.house.services.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.storage.entity.SelectedItemStorage;

import java.util.Optional;

public class SelectedItemService {
    private final SelectedItemStorage selectedItemStorage;

    public SelectedItemService(SelectedItemStorage selectedItemStorage) {
        this.selectedItemStorage = selectedItemStorage;
    }

    public Optional<ISelectedItem> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return selectedItemStorage.read(id);
    }
}