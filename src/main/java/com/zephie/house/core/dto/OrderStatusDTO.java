package com.zephie.house.core.dto;

public class OrderStatusDTO {
    private Long ticketId;
    public OrderStatusDTO() {
    }

    public OrderStatusDTO(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
