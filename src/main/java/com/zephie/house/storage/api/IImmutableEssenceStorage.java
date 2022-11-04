package com.zephie.house.storage.api;

import java.util.Collection;
import java.util.Optional;

public interface IImmutableEssenceStorage<T, DTO> {
    T create(DTO dto);

    Optional<T> read(Long id);

    Collection<T> read();

    void delete(Long id);

    boolean isPresent(Long id);
}
