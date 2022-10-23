package com.zephie.house.services.entity;

import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.storage.entity.PizzaInfoStorage;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.services.api.IPizzaInfoService;
import com.zephie.house.util.validators.PizzaInfoValidator;

import java.util.Collection;
import java.util.Optional;

public class PizzaInfoService implements IPizzaInfoService {

    private static volatile PizzaInfoService instance;
    private final IPizzaInfoStorage pizzaInfoStorage;

    private PizzaInfoService() {
        pizzaInfoStorage = PizzaInfoStorage.getInstance();
    }
    @Override
    public Optional<IPizzaInfo> read(Long id) {
        return pizzaInfoStorage.read(id);
    }

    @Override
    public void delete(Long id) {
        pizzaInfoStorage.delete(id);
    }

    @Override
    public Collection<IPizzaInfo> get() {
        return pizzaInfoStorage.get();
    }

    @Override
    public void create(PizzaInfoDTO pizzaInfo) {
        PizzaInfoValidator.validate(pizzaInfo);
        pizzaInfoStorage.create(pizzaInfo);
    }

    @Override
    public void update(Long id, PizzaInfoDTO pizzaInfo) {
        PizzaInfoValidator.validate(pizzaInfo);
        pizzaInfoStorage.update(id, pizzaInfo);
    }

    public static PizzaInfoService getInstance() {
        if (instance == null) {
            synchronized (PizzaInfoService.class) {
                if (instance == null) {
                    instance = new PizzaInfoService();
                }
            }
        }

        return instance;
    }
}
