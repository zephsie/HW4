package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemMenuDTO {

    private String name;

    private Boolean active;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public SystemMenuDTO() {
    }

    public SystemMenuDTO(String name, Boolean active, LocalDateTime createDate, LocalDateTime updateDate) {
        this.name = name;
        this.active = active;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
