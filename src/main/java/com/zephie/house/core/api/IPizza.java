package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface IPizza {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);
}
