package com.zephie.house.core.builders;

import com.zephie.house.core.api.IOrderStatus;
import com.zephie.house.core.api.IStage;
import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.entity.StageStatus;

import java.time.LocalTime;

public class StageStatusBuilder {
    private IOrderStatus orderStatus;

    private IStage stage;

    private LocalTime startTime;

    private StageStatusBuilder() {
    }

    public static StageStatusBuilder create() {
        return new StageStatusBuilder();
    }

    public StageStatusBuilder setOrderStatus(IOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public StageStatusBuilder setStage(IStage stage) {
        this.stage = stage;
        return this;
    }

    public StageStatusBuilder setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public IStageStatus build() {
        return new StageStatus(orderStatus, stage, startTime);
    }
}