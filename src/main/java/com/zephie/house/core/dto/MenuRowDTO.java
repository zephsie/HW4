package com.zephie.house.core.dto;

import com.zephie.house.core.entity.PizzaInfo;

public class MenuRowDTO {

    private Long pizzaInfoId;

    private Long menuId;

    private double price;

    public MenuRowDTO() {
    }

    public MenuRowDTO(Long pizzaInfoId, Long menuId, double price) {
        this.pizzaInfoId = pizzaInfoId;
        this.menuId = menuId;
        this.price = price;
    }

    public Long getPizzaInfoId() {
        return pizzaInfoId;
    }

    public void setPizzaInfoId(Long pizzaInfoId) {
        this.pizzaInfoId = pizzaInfoId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
