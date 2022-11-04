package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemSelectedItemDTO {
    private Long menuRowId;

    private Integer count;

    private LocalDateTime createDate;

    public SystemSelectedItemDTO() {
    }

    public SystemSelectedItemDTO(Long menuRowId, Integer count, LocalDateTime createDate) {
        this.menuRowId = menuRowId;
        this.count = count;
        this.createDate = createDate;
    }

    public Long getMenuRowId() {
        return menuRowId;
    }

    public void setMenuRowId(Long menuRowId) {
        this.menuRowId = menuRowId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}