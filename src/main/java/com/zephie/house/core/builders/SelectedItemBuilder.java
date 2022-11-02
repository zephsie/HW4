package com.zephie.house.core.builders;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.entity.SelectedItem;

import java.time.LocalDateTime;

public class SelectedItemBuilder {
    private Long id;
    private IMenuRow menuRow;
    private int count;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

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

    public SelectedItemBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    public SelectedItemBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public SelectedItemBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public SelectedItem build() {
        return new SelectedItem(id, menuRow, count, createDate, updateDate);
    }
}
