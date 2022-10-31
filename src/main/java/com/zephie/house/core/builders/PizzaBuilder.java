package com.zephie.house.core.builders;

import com.zephie.house.core.entity.Pizza;

import java.time.LocalDateTime;

public class PizzaBuilder {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private PizzaBuilder() {
    }

    public static PizzaBuilder create() {
        return new PizzaBuilder();
    }

    public PizzaBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PizzaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PizzaBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public PizzaBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public PizzaBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Pizza build() {
        return new Pizza(id, name, description, createDate, updateDate);
    }
}