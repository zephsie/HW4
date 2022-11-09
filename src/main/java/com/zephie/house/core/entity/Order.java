package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.api.IOrder;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Order implements IOrder {
    private Long id;
    private Set<ISelectedItem> items;
    private LocalDateTime createDate;

    public Order(Long id, LocalDateTime createDate, Set<ISelectedItem> items) {
        this.id = id;
        this.createDate = createDate;
        this.items = items;
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
    public Set<ISelectedItem> getItems() {
        return items;
    }

    @Override
    public void setItems(Set<ISelectedItem> selectedItems) {
        this.items = selectedItems;
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
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(items, order.items)) return false;
        return Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
