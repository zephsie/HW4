package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.api.IOrder;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket implements ITicket {
    private Long id;

    private String ticketNumber;

    private IOrder order;

    private LocalDateTime createDate;

    public Ticket() {
    }

    public Ticket(Long id, String ticketNumber, IOrder order, LocalDateTime createDate) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.order = order;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public IOrder getOrder() {
        return order;
    }

    public void setOrder(IOrder order) {
        this.order = order;
    }

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createAt) {
        this.createDate = createAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", number='" + ticketNumber + '\'' +
                ", order=" + order +
                ", createAt=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!Objects.equals(id, ticket.id)) return false;
        if (!Objects.equals(ticketNumber, ticket.ticketNumber)) return false;
        if (!Objects.equals(order, ticket.order)) return false;
        return Objects.equals(createDate, ticket.createDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ticketNumber != null ? ticketNumber.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}