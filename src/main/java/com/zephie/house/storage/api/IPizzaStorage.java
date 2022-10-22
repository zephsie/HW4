package com.zephie.house.storage.api;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.PizzaDTO;

import java.util.Optional;

public interface IPizzaStorage extends IEssenceStorage<IPizza> {

    void create(PizzaDTO pizza);

    void update(Long id, PizzaDTO pizza);

    Optional<IPizza> read(String name);

    void update(String name, PizzaDTO t);

    void delete(String name);
}
