package com.zephie.house.util.validators;

import com.zephie.house.core.dto.PizzaInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class PizzaInfoValidator {
    public static void validate(PizzaInfoDTO pizza) {
        List<String> errors = new ArrayList<>();

        if (pizza.getSize() < 0) {
            errors.add("Size must be positive");
        }

        if (pizza.getPizzaId() == null) {
            errors.add("Base must be specified");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
