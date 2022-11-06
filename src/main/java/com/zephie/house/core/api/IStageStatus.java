package com.zephie.house.core.api;

import java.time.LocalTime;

public interface IStageStatus {

    IOrderStatus getOrderStatus();

    void setOrderStatus(IOrderStatus order);

    IStage getStage();

    void setStage(IStage stage);

    LocalTime getStartTime();

    void setStartTime(LocalTime startTime);
}