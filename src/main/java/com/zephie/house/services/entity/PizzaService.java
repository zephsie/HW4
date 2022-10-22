package com.zephie.house.services.entity;

import com.zephie.house.storage.entity.PizzaStorage;
import com.zephie.house.util.validators.PizzaValidator;
import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.services.api.IPizzaService;
import com.zephie.house.storage.api.IPizzaStorage;

import java.util.Collection;
import java.util.Optional;

public class PizzaService implements IPizzaService {

    private static volatile PizzaService instance;

    private final IPizzaStorage storage;

    private PizzaService() {
        storage = PizzaStorage.getInstance();
    }

    @Override
    public Optional<IPizza> read(Long id) {
        return storage.read(id);
    }

    @Override
    public void create(PizzaDTO pizza) {
        PizzaValidator.validate(pizza);
        storage.create(pizza);
    }

    @Override
    public void update(Long id, PizzaDTO pizza) {
        PizzaValidator.validate(pizza);
        storage.update(id, pizza);
    }

    @Override
    public void delete(Long id) {
        storage.delete(id);
    }

    @Override
    public Collection<IPizza> get() {
        try {
            return storage.get();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting Pizza");
        }
    }

    @Override
    public Optional<IPizza> read(String name) {
        try {
            return storage.read(name);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading Pizza");
        }
    }

    @Override
    public void update(String name, PizzaDTO pizza) {
        PizzaValidator.validate(pizza);

        try {
            storage.update(name, pizza);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating Pizza");
        }
    }

    @Override
    public void delete(String name) {
        try {
            storage.delete(name);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting Pizza");
        }
    }

    public static PizzaService getInstance() {
        if (instance == null) {
            synchronized (PizzaService.class) {
                if (instance == null) {
                    instance = new PizzaService();
                }
            }
        }
        return instance;
    }
}
