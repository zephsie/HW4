package com.zephie.house.services.api;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.PizzaDTO;

import java.util.Optional;

public interface IPizzaService extends IEssenceService<IPizza> {
    void create(PizzaDTO pizza);

    void update(Long id, PizzaDTO pizza);

    Optional<IPizza> read(String name);

    void update(String name, PizzaDTO pizza);

    void delete(String name);
}
