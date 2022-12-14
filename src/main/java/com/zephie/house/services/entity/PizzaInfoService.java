package com.zephie.house.services.entity;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.core.dto.SystemPizzaInfoDTO;
import com.zephie.house.services.api.IPizzaInfoService;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.api.IValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class PizzaInfoService implements IPizzaInfoService {
    private final IPizzaInfoStorage storage;

    private final IPizzaStorage pizzaStorage;

    private final IValidator<PizzaInfoDTO> validator;

    public PizzaInfoService(IPizzaInfoStorage storage, IPizzaStorage pizzaStorage, IValidator<PizzaInfoDTO> validator) {
        this.storage = storage;
        this.pizzaStorage = pizzaStorage;
        this.validator = validator;
    }

    @Override
    public IPizzaInfo create(PizzaInfoDTO pizzaInfoDTO) {
        validator.validate(pizzaInfoDTO);

        if (!pizzaStorage.isPresent(pizzaInfoDTO.getPizzaId())) {
            throw new FKNotFound("Pizza with id " + pizzaInfoDTO.getPizzaId() + " not found");
        }

        return storage.create(new SystemPizzaInfoDTO(pizzaInfoDTO.getPizzaId(),
                pizzaInfoDTO.getSize(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<IPizzaInfo> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return storage.read(id);
    }

    @Override
    public Collection<IPizzaInfo> read() {
        return storage.read();
    }

    @Override
    public IPizzaInfo update(Long id, PizzaInfoDTO pizzaInfoDTO, LocalDateTime dateUpdate) {
        validator.validate(pizzaInfoDTO);

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        Optional<IPizzaInfo> optionalPizzaInfo = storage.read(id);

        if (optionalPizzaInfo.isEmpty()) {
            throw new NotFoundException("PizzaInfo with id " + id + " not found");
        }

        IPizzaInfo pizzaInfo = optionalPizzaInfo.get();

        if (!pizzaInfo.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("PizzaInfo with id " + id + " has been updated");
        }

        if (!pizzaStorage.isPresent(pizzaInfoDTO.getPizzaId())) {
            throw new FKNotFound("Pizza with id " + pizzaInfoDTO.getPizzaId() + " not found");
        }

        return storage.update(id, new SystemPizzaInfoDTO(pizzaInfoDTO.getPizzaId(),
                        pizzaInfoDTO.getSize(),
                        pizzaInfo.getCreateDate(),
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

        Optional<IPizzaInfo> optionalPizzaInfo = storage.read(id);

        if (optionalPizzaInfo.isEmpty()) {
            throw new NotFoundException("PizzaInfo with id " + id + " not found");
        }

        IPizzaInfo pizzaInfo = optionalPizzaInfo.get();

        if (!pizzaInfo.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("PizzaInfo with id " + id + " has been updated");
        }

        storage.delete(id, dateUpdate);
    }
}