package com.zephie.house.storage.api;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;

public interface IMenuRowStorage extends IEssenceStorage<IMenuRow> {
    void create(MenuRowDTO menuRow);

    void update(Long id, MenuRowDTO menuRow);
}
