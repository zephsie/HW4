package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemStageDTO {
    private String description;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public SystemStageDTO() {
    }

    public SystemStageDTO(String description, LocalDateTime createDate, LocalDateTime updateDate) {
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
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
