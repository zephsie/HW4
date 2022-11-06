package com.zephie.house.core.dto;

public class OrderStatusDTO {
    private Long ticketId;

    private Long stageId;

    public OrderStatusDTO() {
    }

    public OrderStatusDTO(Long ticketId, Long stageId) {
        this.ticketId = ticketId;
        this.stageId = stageId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }
}
