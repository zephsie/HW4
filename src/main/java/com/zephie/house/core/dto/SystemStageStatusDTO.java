package com.zephie.house.core.dto;

import java.time.LocalTime;

public class SystemStageStatusDTO {
    private Long orderStatusId;

    private Long stageId;

    private LocalTime startTime;

    public SystemStageStatusDTO() {
    }

    public SystemStageStatusDTO(Long orderStatusId, Long stageId, LocalTime startTime) {
        this.orderStatusId = orderStatusId;
        this.stageId = stageId;
        this.startTime = startTime;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
