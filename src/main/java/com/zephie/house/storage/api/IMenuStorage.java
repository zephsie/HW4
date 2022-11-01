package com.zephie.house.storage.api;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.dto.SystemMenuDTO;

import java.util.Optional;

public interface IMenuStorage extends IEssenceStorage<IMenu, SystemMenuDTO> {
    Optional<IMenu> read(String name);
}