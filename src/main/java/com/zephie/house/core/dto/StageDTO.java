package com.zephie.house.core.dto;

public class StageDTO {
    private String description;

    public StageDTO() {
    }

    public StageDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
