package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemOrderStatusDTO {
    private Long ticketId;

    private LocalDateTime createDate;

    public SystemOrderStatusDTO() {
    }

    public SystemOrderStatusDTO(Long ticketId, LocalDateTime createDate) {
        this.ticketId = ticketId;
        this.createDate = createDate;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
