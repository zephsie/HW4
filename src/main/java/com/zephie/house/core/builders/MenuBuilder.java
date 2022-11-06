package com.zephie.house.core.builders;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.entity.Menu;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MenuBuilder {
    private Long id;

    private String name;

    private Boolean active;

    private Set<IMenuRow> rows;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private MenuBuilder() {
    }

    public static MenuBuilder create() {
        return new MenuBuilder();
    }

    public MenuBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public MenuBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MenuBuilder setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public MenuBuilder setRows(Collection<IMenuRow> rows) {
        this.rows = new HashSet<>(rows);
        return this;
    }

    public MenuBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public MenuBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public IMenu build() {
        return new Menu(id, name, active, rows, createDate, updateDate);
    }
}