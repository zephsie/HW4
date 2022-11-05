package com.zephie.house.core.builders;

import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.entity.Ticket;

import java.time.LocalDateTime;

public class TicketBuilder {
    private Long id;

    private String ticketNumber;

    private IOrder order;

    private LocalDateTime createDate;

    private TicketBuilder() {
    }

    public static TicketBuilder create() {
        return new TicketBuilder();
    }

    public TicketBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TicketBuilder setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public TicketBuilder setOrder(IOrder order) {
        this.order = order;
        return this;
    }

    public TicketBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public Ticket build() {
        return new Ticket(id, ticketNumber, order, createDate);
    }
}
