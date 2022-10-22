package com.zephie.house.services.api;

import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.core.api.IPizzaInfo;

public interface IPizzaInfoService extends IEssenceService<IPizzaInfo> {

    void create(PizzaInfoDTO pizza);

    void update(Long id, PizzaInfoDTO pizza);
}