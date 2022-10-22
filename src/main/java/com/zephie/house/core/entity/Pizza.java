package com.zephie.house.core.entity;

import com.zephie.house.core.api.IPizza;

public class Pizza implements IPizza {
    private Long id;

    private String name;

    private String description;

    public Pizza() {
    }

    public Pizza(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
