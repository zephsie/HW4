package com.zephie.house.core.dto;

public class PizzaInfoDTO {
    private int size;

    private Long pizzaId;

    public PizzaInfoDTO() {
    }

    public PizzaInfoDTO(int size, Long pizzaId) {
        this.size = size;
        this.pizzaId = pizzaId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }
}
