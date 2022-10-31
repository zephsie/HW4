package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class PizzaInfo implements IPizzaInfo {

    private Long id;

    private IPizza pizza;

    private int size;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public PizzaInfo() {
    }

    public PizzaInfo(Long id, IPizza pizza, int size, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.pizza = pizza;
        this.size = size;
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
    public IPizza getPizza() {
        return pizza;
    }

    @Override
    public void setPizza(IPizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "PizzaInfo{" +
                "id=" + id +
                ", pizza=" + pizza +
                ", size=" + size +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PizzaInfo pizzaInfo = (PizzaInfo) o;

        return Objects.equals(id, pizzaInfo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
