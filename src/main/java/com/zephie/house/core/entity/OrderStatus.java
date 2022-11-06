package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.ITicket;
import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStage;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class OrderStatus implements IOrderStatus {
    private Long id;

    private ITicket ticket;

    private Boolean isDone;

    private Map<IStage, LocalTime> history;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public OrderStatus() {
    }

    public OrderStatus(Long id, Ticket ticket, Boolean isDone, Map<IStage, LocalTime> history, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.ticket = ticket;
        this.isDone = isDone;
        this.history = history;
        this.createDate = createDate;
        this.updateDate = updateDate;
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
    public Boolean getIsDone() {
        return isDone;
    }

    @Override
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public Map<IStage, LocalTime> getHistory() {
        return history;
    }

    @Override
    public void setHistory(Map<IStage, LocalTime> history) {
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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(LocalDateTime updateAt) {
        this.updateDate = updateAt;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", isDone=" + isDone +
                ", history=" + history +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
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
