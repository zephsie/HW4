package com.zephie.house.storage.api;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.dto.PizzaInfoDTO;

public interface IPizzaInfoStorage extends IEssenceStorage<IPizzaInfo> {
    void create(PizzaInfoDTO pizza);

    void update(Long id, PizzaInfoDTO pizza);
}

