package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.builders.PizzaInfoBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToPizzaInfoMapper {
    public static IPizzaInfo fullMap(ResultSet resultSet) throws SQLException {
        return PizzaInfoBuilder.create()
                .setId(resultSet.getLong("pizza_info_id"))
                .setPizza(ResultSetToPizzaMapper.partialMap(resultSet))
                .setSize(resultSet.getInt("pizza_info_size"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }

    public static IPizzaInfo partialMap(ResultSet resultSet) throws SQLException {
        return PizzaInfoBuilder.create()
                .setId(resultSet.getLong("pizza_info_id"))
                .setPizza(ResultSetToPizzaMapper.partialMap(resultSet))
                .setSize(resultSet.getInt("pizza_info_size"))
                .build();
    }
}