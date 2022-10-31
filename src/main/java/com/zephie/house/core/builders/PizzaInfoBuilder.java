package com.zephie.house.core.builders;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.entity.PizzaInfo;

import java.time.LocalDateTime;

public class PizzaInfoBuilder {
    private Long id;

    private IPizza pizza;

    private int size;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private PizzaInfoBuilder() {
    }

    public static PizzaInfoBuilder create() {
        return new PizzaInfoBuilder();
    }

    public PizzaInfoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PizzaInfoBuilder setPizza(IPizza pizza) {
        this.pizza = pizza;
        return this;
    }

    public PizzaInfoBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public PizzaInfoBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public PizzaInfoBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public PizzaInfo build() {
        return new PizzaInfo(id, pizza, size, createDate, updateDate);
    }
}
