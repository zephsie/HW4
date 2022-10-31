package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemMenuRowDTO {
    private Long pizzaInfoId;
    private Long menuId;
    private double price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public SystemMenuRowDTO() {
    }

    public SystemMenuRowDTO(Long pizzaInfoId, Long menuId, double price, LocalDateTime createDate, LocalDateTime updateDate) {
        this.pizzaInfoId = pizzaInfoId;
        this.menuId = menuId;
        this.price = price;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getPizzaInfoId() {
        return pizzaInfoId;
    }

    public void setPizzaInfoId(Long pizzaInfoId) {
        this.pizzaInfoId = pizzaInfoId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
