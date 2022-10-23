package com.zephie.house.util.validators;

import com.zephie.house.core.dto.MenuRowDTO;

import java.util.ArrayList;
import java.util.List;

public class MenuRowValidator {

    public static void validate(MenuRowDTO menuRow) {
        List<String> errors = new ArrayList<>();

        if (menuRow.getPizzaInfoId() == null) {
            errors.add("Pizza must be specified");
        }

        if (Math.abs(menuRow.getPrice()) < 0.0001) {
            errors.add("Price must be positive");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
