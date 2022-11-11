package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.StageStatusDTO;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;

public class BasicStageStatusValidator implements IValidator<StageStatusDTO> {
    @Override
    public void validate(StageStatusDTO stageStatusDTO) {
        ArrayList<String> errors = new ArrayList<>();

        if (stageStatusDTO == null) {
            errors.add("StageStatusDTO cannot be null");
        } else {
            if (stageStatusDTO.getStageId() == null) {
                errors.add("StageId cannot be null");
            }

            if (stageStatusDTO.getOrderStatusId() == null) {
                errors.add("OrderStatusId cannot be null");
            }
        }

        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
