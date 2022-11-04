package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface ISelectedItem {
    Long getId();

    void setId(Long id);

    IMenuRow getRow();

    void setRow(IMenuRow row);

    IOrder getOrder();

    void setOrder(IOrder order);

    Integer getCount();

    void setCount(Integer count);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);
}