package com.zephie.house.core.builders;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.entity.SelectedItem;

import java.time.LocalDateTime;

public class SelectedItemBuilder {
    private Long id;
    private IMenuRow menuRow;

    private IOrder order;
    private Integer count;
    private LocalDateTime createDate;

    private SelectedItemBuilder() {
    }

    public static SelectedItemBuilder create() {
        return new SelectedItemBuilder();
    }

    public SelectedItemBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SelectedItemBuilder setMenuRow(IMenuRow menuRow) {
        this.menuRow = menuRow;
        return this;
    }

    public SelectedItemBuilder setCount(Integer count) {
        this.count = count;
        return this;
    }

    public SelectedItemBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public SelectedItemBuilder setOrder(IOrder order) {
        this.order = order;
        return this;
    }

    public ISelectedItem build() {
        return new SelectedItem(id, menuRow, order, count, createDate);
    }
}
