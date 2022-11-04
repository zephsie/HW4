package com.zephie.house.core.builders;

import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.entity.Order;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderBuilder {
    private Long id;
    private Set<ISelectedItem> items;
    private LocalDateTime createDate;

    private OrderBuilder() {
    }

    public static OrderBuilder create() {
        return new OrderBuilder();
    }

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setSelectedItems(Set<ISelectedItem> items) {
        this.items = items;
        return this;
    }

    public OrderBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public IOrder build() {
        return new Order(id, createDate, items);
    }
}
