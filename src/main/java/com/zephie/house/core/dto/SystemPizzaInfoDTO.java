package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemPizzaInfoDTO {
    private Long pizzaId;

    private int size;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public SystemPizzaInfoDTO() {
    }

    public SystemPizzaInfoDTO(Long pizzaId, int size, LocalDateTime createDate, LocalDateTime updateDate) {
        this.pizzaId = pizzaId;
        this.size = size;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
