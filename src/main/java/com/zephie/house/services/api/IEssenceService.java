package com.zephie.house.services.api;

import java.util.Collection;
import java.util.Optional;

public interface IEssenceService<T> {
    Optional<T> read(Long id);

    void delete(Long id);

    Collection<T> get();
}