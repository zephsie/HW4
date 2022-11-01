package com.zephie.house.services.entity;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.core.dto.SystemPizzaDTO;
import com.zephie.house.services.api.IPizzaService;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.PizzaValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class PizzaService implements IPizzaService {

    private final IPizzaStorage storage;

    public PizzaService(IPizzaStorage storage) {
        this.storage = storage;
    }

    @Override
    public IPizza create(PizzaDTO pizzaDTO) {
        PizzaValidator.validate(pizzaDTO);

        if (storage.read(pizzaDTO.getName()).isPresent()) {
            throw new NotUniqueException("Pizza with name " + pizzaDTO.getName() + " already exists");
        }

        return storage.create(new SystemPizzaDTO(pizzaDTO.getName(),
                pizzaDTO.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<IPizza> read(Long id) {
        return storage.read(id);
    }

    @Override
    public Collection<IPizza> read() {
        return storage.read();
    }

    @Override
    public IPizza update(Long id, PizzaDTO pizzaDTO, LocalDateTime dateUpdate) {
        PizzaValidator.validate(pizzaDTO);

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<IPizza> optionalPizza = storage.read(id);

        if (optionalPizza.isEmpty()) {
            throw new NotFoundException("Pizza with id " + id + " not found");
        }

        Optional<IPizza> optionalPizzaByName = storage.read(pizzaDTO.getName());

        if (optionalPizzaByName.isPresent() && !optionalPizzaByName.get().getId().equals(id)) {
            throw new NotUniqueException("Pizza with name " + pizzaDTO.getName() + " already exists");
        }

        IPizza pizza = optionalPizza.get();

        if (!pizza.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Pizza with id " + id + " was updated");
        }

        return storage.update(id, new SystemPizzaDTO(pizzaDTO.getName(),
                        pizzaDTO.getDescription(),
                        pizza.getCreateDate(),
                        LocalDateTime.now()),
                dateUpdate);
    }

    @Override
    public void delete(Long id, LocalDateTime dateUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<IPizza> optionalPizza = storage.read(id);

        if (optionalPizza.isEmpty()) {
            throw new NotFoundException("Pizza with id " + id + " not found");
        }

        IPizza pizza = optionalPizza.get();

        if (!pizza.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Pizza with id " + id + " was updated");
        }

        storage.delete(id, dateUpdate);
    }
}