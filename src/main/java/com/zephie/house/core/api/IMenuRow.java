package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface IMenuRow {

    Long getId();

    void setId(Long id);

    IPizzaInfo getPizzaInfo();

    void setPizzaInfo(IPizzaInfo pizzaInfo);

    IMenu getMenu();

    void setMenu(IMenu menu);

    double getPrice();

    void setPrice(double price);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);
}
