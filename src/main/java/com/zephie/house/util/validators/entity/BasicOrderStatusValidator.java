package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.OrderStatusDTO;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicOrderStatusValidator implements IValidator<OrderStatusDTO> {
    @Override
    public void validate(OrderStatusDTO orderStatusDTO) {
        List<String> errors = new ArrayList<>();

        if (orderStatusDTO == null) {
            errors.add("OrderStatusDTO is null");
        } else {
            if (orderStatusDTO.getTicketId() == null) {
                errors.add("TicketId is null");
            }
        }

        if (errors.size() > 0) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
