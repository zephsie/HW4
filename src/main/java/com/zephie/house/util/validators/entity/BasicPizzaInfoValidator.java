package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicPizzaInfoValidator implements IValidator<PizzaInfoDTO> {
    @Override
    public void validate(PizzaInfoDTO pizzaInfoDTO) {
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