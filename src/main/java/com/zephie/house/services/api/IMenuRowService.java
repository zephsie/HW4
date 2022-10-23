package com.zephie.house.services.api;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.MenuRowDTO;

public interface IMenuRowService extends IEssenceService<IMenuRow> {
    void create(MenuRowDTO menuRow);

    void update(Long id, MenuRowDTO menuRow);
}
