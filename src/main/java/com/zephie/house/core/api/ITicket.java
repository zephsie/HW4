package com.zephie.house.core.api;

import java.time.LocalDateTime;

public interface ITicket {

    Long getId();

    void setId(Long id);

    String getTicketNumber();

    void setTicketNumber(String ticketNumber);

    IOrder getOrder();

    void setOrder(IOrder order);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createAt);
}