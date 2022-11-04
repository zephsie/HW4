package com.zephie.house.services.entity;

import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.dto.OrderDTO;
import com.zephie.house.core.dto.SystemOrderDTO;
import com.zephie.house.core.dto.SystemSelectedItemDTO;
import com.zephie.house.services.api.IOrderService;
import com.zephie.house.storage.api.IMenuRowStorage;
import com.zephie.house.storage.api.IOrderStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.validators.BasicOrderValidator;

import java.time.LocalDateTime;
import java.util.*;

public class OrderService implements IOrderService {
    private final IOrderStorage storage;
    private final IMenuRowStorage menuRowStorage;

    public OrderService(IOrderStorage storage, IMenuRowStorage menuRowStorage) {
        this.storage = storage;
        this.menuRowStorage = menuRowStorage;
    }

    @Override
    public IOrder create(OrderDTO orderDTO) {
        BasicOrderValidator.validate(orderDTO);

        orderDTO.getItems().forEach(item -> {
            if (!menuRowStorage.isAvailable(item.getMenuRowId())) {
                throw new FKNotFound("MenuRow with id cannot be used");
            }
        });

        SystemOrderDTO systemOrderDTO = new SystemOrderDTO();

        Set<SystemSelectedItemDTO> systemItems = new HashSet<>();

        orderDTO.getItems().forEach(item -> systemItems.add(new SystemSelectedItemDTO(
                item.getMenuRowId(),
                item.getCount(),
                LocalDateTime.now())
        ));

        systemOrderDTO.setItems(systemItems);
        systemOrderDTO.setCreateDate(LocalDateTime.now());

        return storage.create(systemOrderDTO);
    }

    @Override
    public Optional<IOrder> read(Long id) {
        return storage.read(id);
    }

    @Override
    public Collection<IOrder> read() {
        return storage.read();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        if (!storage.isPresent(id)) {
            throw new NotFoundException("Order with id " + id + " not found");
        }

        storage.delete(id);
    }
}
