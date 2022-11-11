package com.zephie.house.services.entity;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.dto.OrderStatusDTO;
import com.zephie.house.core.dto.StageStatusDTO;
import com.zephie.house.core.dto.SystemOrderStatusDTO;
import com.zephie.house.core.dto.SystemStageStatusDTO;
import com.zephie.house.services.api.IOrderStatusService;
import com.zephie.house.storage.api.IOrderStatusStorage;
import com.zephie.house.storage.api.IStageStorage;
import com.zephie.house.storage.api.ITicketStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
import com.zephie.house.util.validators.api.IValidator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

public class OrderStatusService implements IOrderStatusService {
    private final IOrderStatusStorage orderStatusStorage;

    private final ITicketStorage ticketStorage;

    private final IStageStorage stageStorage;

    private final IValidator<OrderStatusDTO> validator;

    private final IValidator<StageStatusDTO> stageStatusValidator;

    public OrderStatusService(IOrderStatusStorage orderStatusStorage, ITicketStorage ticketStorage, IStageStorage stageStorage, IValidator<OrderStatusDTO> validator, IValidator<StageStatusDTO> stageStatusValidator) {
        this.orderStatusStorage = orderStatusStorage;
        this.ticketStorage = ticketStorage;
        this.stageStorage = stageStorage;
        this.validator = validator;
        this.stageStatusValidator = stageStatusValidator;
    }

    @Override
    public IOrderStatus create(OrderStatusDTO orderStatusDTO) {
        validator.validate(orderStatusDTO);

        if (!ticketStorage.isPresent(orderStatusDTO.getTicketId())) {
            throw new FKNotFound("Ticket with id " + orderStatusDTO.getTicketId() + " does not exist");
        }

        if (orderStatusStorage.isPresentByTicket(orderStatusDTO.getTicketId())) {
            throw new NotUniqueException("Ticket with id " + orderStatusDTO.getTicketId() + " already has an order status");
        }

        return orderStatusStorage.create(new SystemOrderStatusDTO(
                orderStatusDTO.getTicketId(),
                LocalDateTime.now()
        ));
    }

    @Override
    public Optional<IOrderStatus> read(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return orderStatusStorage.read(id);
    }

    @Override
    public Collection<IOrderStatus> read() {
        return orderStatusStorage.read();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (!orderStatusStorage.isPresent(id)) {
            throw new NotFoundException("Order status with id " + id + " does not exist");
        }

        orderStatusStorage.delete(id);
    }

    @Override
    public IOrderStatus addStage(StageStatusDTO StageStatusDTO) {
        stageStatusValidator.validate(StageStatusDTO);

        if (!orderStatusStorage.isPresent(StageStatusDTO.getOrderStatusId())) {
            throw new FKNotFound("OrderStatus with id " + StageStatusDTO.getOrderStatusId() + " does not exist");
        }

        if (!stageStorage.isPresent(StageStatusDTO.getStageId())) {
            throw new FKNotFound("Stage with id " + StageStatusDTO.getStageId() + " does not exist");
        }

        if (orderStatusStorage.isStageStatusPresent(StageStatusDTO.getOrderStatusId(), StageStatusDTO.getStageId())) {
            throw new NotUniqueException("StageStatus with orderStatusId " + StageStatusDTO.getOrderStatusId() + " and stageId " + StageStatusDTO.getStageId() + " already exists");
        }

        return orderStatusStorage.addStage(new SystemStageStatusDTO(
                StageStatusDTO.getOrderStatusId(),
                StageStatusDTO.getStageId(),
                LocalTime.now()
        ));
    }
}