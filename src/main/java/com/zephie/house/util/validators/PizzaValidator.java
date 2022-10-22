package com.zephie.house.util.validators;

import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class PizzaValidator {
    public static void validate(PizzaDTO pizza) {
        List<String> errors = new ArrayList<>();
        if (pizza.getName() == null || pizza.getName().isBlank()) {
            errors.add("Name is empty");
        }

        if (pizza.getDescription() == null || pizza.getDescription().isBlank()) {
            errors.add("Description is empty");
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
