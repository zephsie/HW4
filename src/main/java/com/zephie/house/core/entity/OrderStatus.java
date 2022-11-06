package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.api.ITicket;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class OrderStatus implements IOrderStatus {
    private Long id;

    private ITicket ticket;

    private Set<IStageStatus> history;

    private LocalDateTime createDate;

    public OrderStatus() {
    }

    public OrderStatus(Long id, ITicket ticket, Set<IStageStatus> history, LocalDateTime createDate) {
        this.id = id;
        this.ticket = ticket;
        this.history = history;
        this.createDate = createDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ITicket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(ITicket ticket) {
        this.ticket = ticket;
    }

    @Override
    public Set<IStageStatus> getHistory() {
        return history;
    }

    @Override
    public void setHistory(Set<IStageStatus> history) {
        this.history = history;
    }

    @Override
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(LocalDateTime createAt) {
        this.createDate = createAt;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", history=" + history +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatus that = (OrderStatus) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
