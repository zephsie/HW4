package com.zephie.house.util.validators;

import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class PizzaInfoValidator {
    public static void validate(PizzaInfoDTO pizzaInfoDTO) {
        List<String> errors = new ArrayList<>();

        if (pizzaInfoDTO == null) {
            errors.add("PizzaInfoDTO is null");
        } else {
            if (pizzaInfoDTO.getPizzaId() == null) {
                errors.add("Pizza ID is empty");
            }

            if (pizzaInfoDTO.getSize() < 1E-6) {
                errors.add("Size is too small");
            }
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}