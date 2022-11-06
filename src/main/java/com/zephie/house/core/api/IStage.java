package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface IStage {

    Long getId();

    void setId(Long id);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createAt);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateAt);
}
