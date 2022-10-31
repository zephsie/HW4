package com.zephie.house.core.api;

import java.time.LocalDateTime;
import java.util.Set;

public interface IMenu {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Boolean getActive();

    void setActive(Boolean active);

    Set<IMenuRow> getRows();

    void setRows(Set<IMenuRow> rows);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);
}