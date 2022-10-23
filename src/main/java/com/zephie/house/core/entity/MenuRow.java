package com.zephie.house.core.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.api.IPizzaInfo;

public class MenuRow implements IMenuRow {
    private Long id;
    private IPizzaInfo pizzaInfo;
    private double price;

    public MenuRow() {
    }

    public MenuRow(Long id, IPizzaInfo pizzaInfo, double price) {
        this.id = id;
        this.pizzaInfo = pizzaInfo;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IPizzaInfo getInfo() {
        return pizzaInfo;
    }

    public void setInfo(IPizzaInfo pizzaInfo) {
        this.pizzaInfo = pizzaInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
