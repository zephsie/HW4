package com.zephie.house.util.validators;

import com.zephie.house.core.dto.OrderDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class BasicOrderValidator {
    public static void validate(OrderDTO orderDTO) {
        List<String> errors = new ArrayList<>();

        if (orderDTO == null) {
            errors.add("OrderDTO is null");
        } else {
            if (orderDTO.getItems().size() < 1) {
                errors.add("Order must have at least one item");
            }

            orderDTO.getItems().forEach(item -> {
                if (item.getCount() < 1) {
                    errors.add("Item count must be at least 1");
                }
            });
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
