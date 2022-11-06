package com.zephie.house.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.util.json.CustomLocalDateTimeDesSerializer;
import com.zephie.house.util.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class MenuRow implements IMenuRow {
    private Long id;

    private IPizzaInfo pizzaInfo;

    private IMenu menu;

    private double price;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public MenuRow() {
    }

    public MenuRow(Long id, IPizzaInfo pizzaInfo, IMenu menu, double price, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.pizzaInfo = pizzaInfo;
        this.menu = menu;
        this.price = price;
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
    public IPizzaInfo getPizzaInfo() {
        return pizzaInfo;
    }

    @Override
    public void setPizzaInfo(IPizzaInfo pizzaInfo) {
        this.pizzaInfo = pizzaInfo;
    }

    @Override
    public IMenu getMenu() {
        return menu;
    }

    @Override
    public void setMenu(IMenu menu) {
        this.menu = menu;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
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
        return "MenuRow{" +
                "id=" + id +
                ", pizzaInfo=" + pizzaInfo +
                ", menu=" + menu +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuRow menuRow = (MenuRow) o;

        return Objects.equals(id, menuRow.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}