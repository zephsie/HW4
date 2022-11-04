package com.zephie.house.core.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class SystemOrderDTO {
    private Set<SystemSelectedItemDTO> items;
    private LocalDateTime createDate;

    public SystemOrderDTO() {
    }

    public SystemOrderDTO(Set<SystemSelectedItemDTO> items, LocalDateTime createDate) {
        this.items = items;
        this.createDate = createDate;
    }

    public Set<SystemSelectedItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<SystemSelectedItemDTO> items) {
        this.items = items;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}