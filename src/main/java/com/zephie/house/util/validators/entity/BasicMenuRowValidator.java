package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.MenuRowDTO;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicMenuRowValidator implements IValidator<MenuRowDTO> {
    @Override
    public void validate(MenuRowDTO menuRowDTO) {
        List<String> errors = new ArrayList<>();

        if (menuRowDTO == null) {
            errors.add("MenuRowDTO cannot be null");
        } else {
            if (menuRowDTO.getPrice() <= 0) {
                errors.add("Price cannot be negative");
            }
        }

        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}