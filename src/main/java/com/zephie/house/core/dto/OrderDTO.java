package com.zephie.house.core.dto;

import java.util.Set;

public class OrderDTO {
    private Set<SelectedItemDTO> items;

    public OrderDTO() {
    }

    public OrderDTO(Set<SelectedItemDTO> items) {
        this.items = items;
    }

    public Set<SelectedItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<SelectedItemDTO> items) {
        this.items = items;
    }
}