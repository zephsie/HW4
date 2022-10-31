package com.zephie.house.core.builders;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.entity.MenuRow;

import java.time.LocalDateTime;

public class MenuRowBuilder {
    private Long id;

    private IPizzaInfo pizzaInfo;

    private IMenu menu;

    private double price;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static MenuRowBuilder create() {
        return new MenuRowBuilder();
    }

    public MenuRowBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public MenuRowBuilder setPizzaInfo(IPizzaInfo pizzaInfo) {
        this.pizzaInfo = pizzaInfo;
        return this;
    }

    public MenuRowBuilder setMenu(IMenu menu) {
        this.menu = menu;
        return this;
    }

    public MenuRowBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public MenuRowBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public MenuRowBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public MenuRow build() {
        return new MenuRow(id, pizzaInfo, menu, price, createDate, updateDate);
    }
}
