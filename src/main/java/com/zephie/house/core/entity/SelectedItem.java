package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class SelectedItem implements ISelectedItem {
    private Long id;
    private IMenuRow row;
    private IOrder order;
    private Integer count;

    private LocalDateTime createDate;

    public SelectedItem() {
    }

    public SelectedItem(Long id, IMenuRow row, IOrder order, Integer count, LocalDateTime createDate) {
        this.id = id;
        this.row = row;
        this.order = order;
        this.count = count;
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
    public IMenuRow getRow() {
        return row;
    }

    @Override
    public void setRow(IMenuRow row) {
        this.row = row;
    }

    @Override
    public IOrder getOrder() {
        return order;
    }

    @Override
    public void setOrder(IOrder order) {
        this.order = order;
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SelectedItem{" +
                "id=" + id +
                ", row=" + row +
                ", order=" + order +
                ", count=" + count +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectedItem that = (SelectedItem) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(row, that.row)) return false;
        if (!Objects.equals(order, that.order)) return false;
        if (!Objects.equals(count, that.count)) return false;
        return Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}