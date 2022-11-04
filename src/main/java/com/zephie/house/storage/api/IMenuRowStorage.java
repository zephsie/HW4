package com.zephie.house.storage.api;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.dto.SystemMenuRowDTO;

public interface IMenuRowStorage extends IEssenceStorage<IMenuRow, SystemMenuRowDTO> {
    Boolean isAvailable(Long id);
}
