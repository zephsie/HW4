package com.zephie.house.storage.api;

import java.util.Collection;
import java.util.Optional;

public interface IEssenceStorage<T> {
    Optional<T> read(Long id);

    void delete(Long id);

    Collection<T> get();
}
