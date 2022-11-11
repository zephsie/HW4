package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.StageDTO;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicStageValidator implements IValidator<StageDTO> {
    @Override
    public void validate(StageDTO stageDTO) {
        List<String> errors = new ArrayList<>();

        if (stageDTO == null) {
            errors.add("StageDTO is null");
        } else {
            if (stageDTO.getDescription() == null || stageDTO.getDescription().isEmpty()) {
                errors.add("Description is null or empty");
            } else if (stageDTO.getDescription().length() > 16) {
                errors.add("Description is too long");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors.toString());
        }
    }
}
