package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IOrder;
import com.zephie.house.core.builders.OrderBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToOrderMapper {
    public IOrder partialMap(ResultSet resultSet) throws SQLException {
        return OrderBuilder.create()
                .setId(resultSet.getLong("order_id"))
                .build();
    }

    public IOrder fullMap(ResultSet resultSet) throws SQLException {
        return OrderBuilder.create()
                .setId(resultSet.getLong("order_id"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .build();
    }
}