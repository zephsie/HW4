package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemSelectedItemDTO {
    private Long menuRowId;

    private int count;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public SystemSelectedItemDTO() {
    }

    public SystemSelectedItemDTO(Long menuRowId, int count, LocalDateTime createDate, LocalDateTime updateDate) {
        this.menuRowId = menuRowId;
        this.count = count;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getMenuRowId() {
        return menuRowId;
    }

    public void setMenuRowId(Long menuRowId) {
        this.menuRowId = menuRowId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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