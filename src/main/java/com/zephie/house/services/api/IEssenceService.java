package com.zephie.house.services.api;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface IEssenceService<T, DTO> {
    T create(DTO dto);

    Optional<T> read(Long id);

    Collection<T> read();

    T update(Long id, DTO dto, LocalDateTime dateUpdate);

    void delete(Long id, LocalDateTime dateUpdate);
}