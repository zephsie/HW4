package com.zephie.house.core.api;

import java.time.LocalDateTime;
import java.util.Set;

public interface IOrderStatus {
    Long getId();

    void setId(Long id);

    ITicket getTicket();

    void setTicket(ITicket ticket);

    Set<IStageStatus> getHistory();

    void setHistory(Set<IStageStatus> history);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createAt);
}
