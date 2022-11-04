package com.zephie.house.core.dto;

public class SelectedItemDTO {
    private Long menuRowId;
    private Integer count;

    public SelectedItemDTO() {
    }

    public SelectedItemDTO(Long menuRowId, Integer count) {
        this.menuRowId = menuRowId;
        this.count = count;
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
}