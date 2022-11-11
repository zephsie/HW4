package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.builders.StageStatusBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToStageStatusMapper {
    private final ResultSetToStageMapper stageMapper;

    public ResultSetToStageStatusMapper(ResultSetToStageMapper stageMapper) {
        this.stageMapper = stageMapper;
    }

    public IStageStatus fullMap(ResultSet resultSet) throws SQLException {
        return StageStatusBuilder.create()
                .setStage(stageMapper.partialMap(resultSet))
                .setStartTime(resultSet.getTime("order_status_stage_start_time").toLocalTime())
                .build();
    }
}