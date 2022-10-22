package com.zephie.house.core.entity;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.api.IPizzaInfo;

public class PizzaInfo implements IPizzaInfo {
    private Long id;

    private int size;

    private IPizza pizza;

    public PizzaInfo() {
    }

    public PizzaInfo(Long id, int size, IPizza pizza) {
        this.id = id;
        this.size = size;
        this.pizza = pizza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public IPizza getBase() {
        return pizza;
    }

    public void setBase(IPizza pizza) {
        this.pizza = pizza;
    }
}
