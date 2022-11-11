package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.builders.PizzaBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToPizzaMapper {
    public IPizza fullMap(ResultSet resultSet) throws SQLException {
        return PizzaBuilder.create()
                .setId(resultSet.getLong("pizza_id"))
                .setName(resultSet.getString("pizza_name"))
                .setDescription(resultSet.getString("pizza_description"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }

    public IPizza partialMap(ResultSet resultSet) throws SQLException {
        return PizzaBuilder.create()
                .setId(resultSet.getLong("pizza_id"))
                .setName(resultSet.getString("pizza_name"))
                .build();
    }
}