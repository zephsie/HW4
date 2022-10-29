package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemPizzaDTO {
    private String name;
    private String description;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public SystemPizzaDTO() {
    }

    public SystemPizzaDTO(String name, String description, LocalDateTime createDate, LocalDateTime updateDate) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
