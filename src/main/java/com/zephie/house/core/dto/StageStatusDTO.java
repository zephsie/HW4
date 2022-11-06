package com.zephie.house.core.dto;

public class StageStatusDTO {
    private Long orderStatusId;

    private Long stageId;

    public StageStatusDTO() {
    }

    public StageStatusDTO(Long orderStatusId, Long stageId) {
        this.orderStatusId = orderStatusId;
        this.stageId = stageId;
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
}
