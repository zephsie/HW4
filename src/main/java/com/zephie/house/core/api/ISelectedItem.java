package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface ISelectedItem {
    Long getId();

    void setId(Long id);

    IMenuRow getRow();

    void setRow(IMenuRow row);

    int getCount();

    void setCount(int count);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);
}