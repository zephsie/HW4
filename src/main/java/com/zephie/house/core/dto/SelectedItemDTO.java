package com.zephie.house.core.dto;

public class SelectedItemDTO {
    private Long menuRowId;

    private int count;

    public SelectedItemDTO() {
    }

    public SelectedItemDTO(Long menuRowId, int count) {
        this.menuRowId = menuRowId;
        this.count = count;
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
}