package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IStageStatus;
import com.zephie.house.core.builders.StageStatusBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToStageStatusMapper {
    public static IStageStatus fullMap(ResultSet resultSet) throws SQLException {
        return StageStatusBuilder.create()
                .setStage(ResultSetToStageMapper.partialMap(resultSet))
                .setStartTime(resultSet.getTime("order_status_stage_start_time").toLocalTime())
                .build();
    }
}