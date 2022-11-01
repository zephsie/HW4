package com.zephie.house.util.validators;

import com.zephie.house.core.dto.MenuRowDTO;

import java.util.ArrayList;
import java.util.List;

public class MenuRowValidator {
    public static void validate(MenuRowDTO menuRowDTO) {
        List<String> errors = new ArrayList<>();

        if (menuRowDTO.getPrice() <= 0) {
            errors.add("Price cannot be negative");
        }

        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}