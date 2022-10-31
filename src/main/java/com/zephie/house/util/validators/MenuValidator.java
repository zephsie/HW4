package com.zephie.house.util.validators;

import com.zephie.house.core.dto.MenuDTO;

import java.util.ArrayList;
import java.util.List;

public class MenuValidator {
    public static void validate(MenuDTO menuDTO) {
        List<String> errors = new ArrayList<>();

        if (menuDTO.getName() == null || menuDTO.getName().isEmpty()) {
            errors.add("Menu name is required");
        }

        if (menuDTO.getName().length() > 32) {
            errors.add("Menu name is too long");
        }

        if (menuDTO.getActive() == null) {
            errors.add("Menu active is required");
        }

        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}