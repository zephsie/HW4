package com.zephie.house.core.dto;

public class MenuRowDTO {

    private Long pizzaInfoId;

    private double price;

    public MenuRowDTO() {
    }

    public MenuRowDTO(Long pizzaInfoId, double price) {
        this.pizzaInfoId = pizzaInfoId;
        this.price = price;
    }

    public Long getPizzaInfoId() {
        return pizzaInfoId;
    }

    public void setPizzaInfoId(Long pizzaInfoId) {
        this.pizzaInfoId = pizzaInfoId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
