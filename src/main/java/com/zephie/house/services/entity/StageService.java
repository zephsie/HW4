package com.zephie.house.services.entity;

import com.zephie.house.core.api.IStage;
import com.zephie.house.core.dto.StageDTO;
import com.zephie.house.core.dto.SystemStageDTO;
import com.zephie.house.services.api.IStageService;
import com.zephie.house.storage.api.IStageStorage;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.BasicStageValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class StageService implements IStageService {
    private final IStageStorage storage;

    public StageService(IStageStorage storage) {
        this.storage = storage;
    }

    @Override
    public IStage create(StageDTO stageDTO) {
        BasicStageValidator.validate(stageDTO);

        if (storage.read(stageDTO.getDescription()).isPresent()) {
            throw new NotUniqueException("Stage with description " + stageDTO.getDescription() + " already exists");
        }

        return storage.create(new SystemStageDTO(stageDTO.getDescription(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<IStage> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return storage.read(id);
    }

    @Override
    public Collection<IStage> read() {
        return storage.read();
    }

    @Override
    public IStage update(Long id, StageDTO stageDTO, LocalDateTime dateUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (dateUpdate == null) {
            throw new IllegalArgumentException("Date update cannot be null");
        }

        BasicStageValidator.validate(stageDTO);

        Optional<IStage> stage = storage.read(id);

        if (stage.isEmpty()) {
            throw new NotFoundException("Stage with id " + id + " does not exist");
        }

        Optional<IStage> stageByDescription = storage.read(stageDTO.getDescription());

        if (stageByDescription.isPresent() && !stageByDescription.get().getId().equals(id)) {
            throw new NotUniqueException("Stage with description " + stageDTO.getDescription() + " already exists");
        }

        IStage stageToUpdate = stage.get();

        if (!stageToUpdate.getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Stage with id " + id + " was updated");
        }

        new SystemStageDTO(stageDTO.getDescription(), stageToUpdate.getCreateDate(), LocalDateTime.now());

        return storage.update(id, new SystemStageDTO(
                        stageDTO.getDescription(),
                        stageToUpdate.getCreateDate(),
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

        Optional<IStage> stage = storage.read(id);

        if (stage.isEmpty()) {
            throw new NotFoundException("Stage with id " + id + " does not exist");
        }

        if (!stage.get().getUpdateDate().equals(dateUpdate)) {
            throw new WrongVersionException("Stage with id " + id + " was updated");
        }

        storage.delete(id, dateUpdate);
    }
}