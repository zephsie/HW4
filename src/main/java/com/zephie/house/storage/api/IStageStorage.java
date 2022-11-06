package com.zephie.house.storage.api;

import com.zephie.house.core.api.IStage;
import com.zephie.house.core.dto.SystemStageDTO;

import java.util.Optional;

public interface IStageStorage extends IEssenceStorage<IStage, SystemStageDTO> {
    Optional<IStage> read(String name);
}