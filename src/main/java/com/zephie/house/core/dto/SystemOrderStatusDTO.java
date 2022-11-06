package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemOrderStatusDTO {
    private Long ticketId;

    private Long stageId;

    private LocalDateTime createDate;

    public SystemOrderStatusDTO() {
    }

    public SystemOrderStatusDTO(Long ticketId, Long stageId, LocalDateTime createDate) {
        this.ticketId = ticketId;
        this.stageId = stageId;
        this.createDate = createDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
