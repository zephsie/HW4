package com.zephie.house.util.validators.entity;

import com.zephie.house.core.dto.TicketDTO;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.validators.api.IValidator;

import java.util.ArrayList;
import java.util.List;

public class BasicTicketValidator implements IValidator<TicketDTO> {
    @Override
    public void validate(TicketDTO ticketDTO) {
        List<String> errors = new ArrayList<>();

        if (ticketDTO == null) {
            errors.add("TicketDTO cannot be null");
        } else {
            if (ticketDTO.getOrderId() == null) {
                errors.add("Order id cannot be null");
            }

            if (ticketDTO.getTicketNumber() == null || ticketDTO.getTicketNumber().isEmpty()) {
                errors.add("Ticket number cannot be null or empty");
            } else if (ticketDTO.getTicketNumber().length() > 10) {
                errors.add("Ticket number cannot be longer than 50 characters");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
