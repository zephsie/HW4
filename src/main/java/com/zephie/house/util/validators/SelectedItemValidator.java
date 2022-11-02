package com.zephie.house.util.validators;

import com.zephie.house.core.dto.SelectedItemDTO;
import com.zephie.house.util.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class SelectedItemValidator {
    public static void validate (SelectedItemDTO selectedItemDTO) {
        List<String> errors = new ArrayList<>();

        if (selectedItemDTO == null) {
            errors.add("SelectedItemDTO is null");
        } else {
            if (selectedItemDTO.getCount() < 1) {
                errors.add("Count is less than 1");
            }
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
