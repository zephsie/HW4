package com.zephie.house.util.validators;

import com.zephie.house.core.dto.StageDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BasicStageValidator {
    public static void validate(StageDTO stageDTO) {
        List<String> errors = new ArrayList<>();

        if (stageDTO == null) {
            errors.add("StageDTO is null");
        } else {
            if (stageDTO.getDescription() == null || stageDTO.getDescription().isEmpty()) {
                errors.add("Description is null or empty");
            }

            if (stageDTO.getDescription().length() > 16) {
                errors.add("Description is too long");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors.toString());
        }
    }
}
