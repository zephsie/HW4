package com.zephie.house.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStage;
import com.zephie.house.core.api.IStageStatus;

import java.time.LocalTime;
import java.util.Objects;

public class StageStatus implements IStageStatus {
    private IOrderStatus orderStatus;

    private IStage stage;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime startTime;

    public StageStatus() {
    }

    public StageStatus(IOrderStatus orderStatus, IStage stage, LocalTime startTime) {
        this.orderStatus = orderStatus;
        this.stage = stage;
        this.startTime = startTime;
    }

    @Override
    public IOrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public void setOrderStatus(IOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public IStage getStage() {
        return stage;
    }

    @Override
    public void setStage(IStage stage) {
        this.stage = stage;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "StageStatus{" +
                "orderStatus=" + orderStatus +
                ", stage=" + stage +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StageStatus that = (StageStatus) o;

        if (!Objects.equals(orderStatus, that.orderStatus)) return false;
        if (!Objects.equals(stage, that.stage)) return false;
        return Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        int result = orderStatus != null ? orderStatus.hashCode() : 0;
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }
}