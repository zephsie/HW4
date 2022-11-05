package com.zephie.house.core.dto;

import java.time.LocalDateTime;

public class SystemTicketDTO {
    private String ticketNumber;

    private Long orderId;

    private LocalDateTime createDate;

    public SystemTicketDTO() {
    }

    public SystemTicketDTO(String ticketNumber, Long orderId, LocalDateTime createDate) {
        this.ticketNumber = ticketNumber;
        this.orderId = orderId;
        this.createDate = createDate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
