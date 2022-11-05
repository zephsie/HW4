package com.zephie.house.services.entity;

import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.dto.SystemTicketDTO;
import com.zephie.house.core.dto.TicketDTO;
import com.zephie.house.services.api.ITicketService;
import com.zephie.house.storage.api.IOrderStorage;
import com.zephie.house.storage.api.ITicketStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.validators.BasicTicketValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class TicketService implements ITicketService {
    private final ITicketStorage ticketStorage;

    private final IOrderStorage orderStorage;

    public TicketService(ITicketStorage ticketStorage, IOrderStorage orderStorage) {
        this.ticketStorage = ticketStorage;
        this.orderStorage = orderStorage;
    }

    @Override
    public ITicket create(TicketDTO ticketDTO) {
        BasicTicketValidator.validate(ticketDTO);

        if (!orderStorage.isPresent(ticketDTO.getOrderId())) {
            throw new FKNotFound("Order with id " + ticketDTO.getOrderId() + " not found");
        }

        if (ticketStorage.isPresentByOrder(ticketDTO.getOrderId())) {
            throw new NotUniqueException("Ticket with order id " + ticketDTO.getOrderId() + " already exists");
        }

        if (ticketStorage.isPresent(ticketDTO.getTicketNumber())) {
            throw new NotUniqueException("Ticket with number " + ticketDTO.getTicketNumber() + " already exists");
        }

        return ticketStorage.create(new SystemTicketDTO(
                ticketDTO.getTicketNumber(),
                ticketDTO.getOrderId(),
                LocalDateTime.now()
        ));
    }

    @Override
    public Optional<ITicket> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return ticketStorage.read(id);
    }

    @Override
    public Collection<ITicket> read() {
        return ticketStorage.read();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (!ticketStorage.isPresent(id)) {
            throw new NotFoundException("Ticket with id " + id + " not found");
        }

        ticketStorage.delete(id);
    }
}