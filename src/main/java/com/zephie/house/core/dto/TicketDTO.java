package com.zephie.house.core.dto;

public class TicketDTO {
    private String ticketNumber;

    private Long orderId;

    public TicketDTO() {
    }

    public TicketDTO(String ticketNumber, Long orderId) {
        this.ticketNumber = ticketNumber;
        this.orderId = orderId;
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
}
