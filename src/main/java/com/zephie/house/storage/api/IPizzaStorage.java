package com.zephie.house.storage.api;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.SystemPizzaDTO;

import java.util.Optional;

public interface IPizzaStorage extends IEssenceStorage<IPizza, SystemPizzaDTO> {
    Optional<IPizza> read(String name);
}
