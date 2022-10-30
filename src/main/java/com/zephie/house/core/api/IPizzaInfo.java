package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface IPizzaInfo {
    Long getId();

    void setId(Long id);

    IPizza getPizza();

    void setPizza(IPizza pizza);

    int getSize();

    void setSize(int size);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);
}
