package com.zephie.house.core.api;

import java.time.LocalDateTime;
import java.util.Set;

public interface IOrder {

    Long getId();

    void setId(Long id);

    Set<ISelectedItem> getItems();

    void setItems(Set<ISelectedItem> selectedItems);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);
}
