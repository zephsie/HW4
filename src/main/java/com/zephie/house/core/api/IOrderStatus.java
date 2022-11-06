package com.zephie.house.core.api;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public interface IOrderStatus {
    Long getId();

    void setId(Long id);

    ITicket getTicket();

    void setTicket(ITicket ticket);

    Boolean getIsDone();

    void setIsDone(Boolean isDone);

    Map<IStage, LocalTime> getHistory();

    void setHistory(Map<IStage, LocalTime> history);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createAt);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateAt);
}
