package com.zephie.house.core.builders;

import com.zephie.house.core.entity.Stage;

import java.time.LocalDateTime;

public class StageBuilder {
    private Long id;

    private String description;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private StageBuilder() {
    }

    public static StageBuilder create() {
        return new StageBuilder();
    }

    public StageBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public StageBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public StageBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public StageBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Stage build() {
        return new Stage(id, description, createDate, updateDate);
    }
}