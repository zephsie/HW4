package com.zephie.house.util.validators;

import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BasicPizzaValidator {
    public static void validate(PizzaDTO pizzaDTO) {
        List<String> errors = new ArrayList<>();

        if (pizzaDTO == null) {
            errors.add("PizzaDTO is null");
        } else {
            if (pizzaDTO.getName() == null || pizzaDTO.getName().isBlank()) {
                errors.add("Name is empty");
            }

            if (pizzaDTO.getName().length() > 32) {
                errors.add("Name is too long");
            }

            if (pizzaDTO.getDescription() == null || pizzaDTO.getDescription().isBlank()) {
                errors.add("Description is empty");
            }

            if (pizzaDTO.getDescription().length() > 128) {
                errors.add("Description is too long");
            }
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}