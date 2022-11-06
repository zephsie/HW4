package com.zephie.house.core.builders;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderStatusBuilder {
    private Long id;

    private ITicket ticket;

    private Set<IStageStatus> history;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private OrderStatusBuilder() {
    }

    public static OrderStatusBuilder create() {
        return new OrderStatusBuilder();
    }

    public OrderStatusBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderStatusBuilder setTicket(ITicket ticket) {
        this.ticket = ticket;
        return this;
    }

    public OrderStatusBuilder setHistory(Set<IStageStatus> history) {
        this.history = history;
        return this;
    }

    public OrderStatusBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public OrderStatusBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public IOrderStatus build() {
        return new OrderStatus(id, ticket, history, createDate);
    }
}
