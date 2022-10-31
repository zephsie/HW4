package com.zephie.house.storage.api;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.dto.SystemMenuDTO;

public interface IMenuStorage extends IEssenceStorage<IMenu, SystemMenuDTO> {
    void read(String name);

    void readWithoutRows(Long id);
}