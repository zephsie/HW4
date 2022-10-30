package com.zephie.house.core.dto;

public class PizzaInfoDTO {
    private Long pizzaId;
    private int size;

    public PizzaInfoDTO() {
    }

    public PizzaInfoDTO(Long pizzaId, int size) {
        this.pizzaId = pizzaId;
        this.size = size;
    }

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
