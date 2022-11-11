package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IStage;
import com.zephie.house.core.builders.StageBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToStageMapper {
    public IStage fullMap(ResultSet resultSet) throws SQLException {
        return StageBuilder.create()
                .setId(resultSet.getLong("stage_id"))
                .setDescription(resultSet.getString("stage_description"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }

    public IStage partialMap(ResultSet resultSet) throws SQLException {
        return StageBuilder.create()
                .setId(resultSet.getLong("stage_id"))
                .setDescription(resultSet.getString("stage_description"))
                .build();
    }
}