package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.MenuDTO;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicMenuValidator implements IValidator<MenuDTO> {
    @Override
    public void validate(MenuDTO menuDTO) {
        List<String> errors = new ArrayList<>();

        if (menuDTO == null) {
            errors.add("MenuDTO cannot be null");
        } else {
            if (menuDTO.getName() == null || menuDTO.getName().isEmpty()) {
                errors.add("Menu name is required");
            } else if (menuDTO.getName().length() > 32) {
                errors.add("Menu name is too long");
            }

            if (menuDTO.getActive() == null) {
                errors.add("Menu active is required");
            }
        }

        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}