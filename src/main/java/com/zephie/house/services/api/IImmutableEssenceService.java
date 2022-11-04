package com.zephie.house.services.api;

import java.util.Collection;
import java.util.Optional;

public interface IImmutableEssenceService<T, DTO> {
    T create(DTO dto);

    Optional<T> read(Long id);

    Collection<T> read();

    void delete(Long id);
}