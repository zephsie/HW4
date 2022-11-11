package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.builders.PizzaInfoBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToPizzaInfoMapper {
    private final ResultSetToPizzaMapper pizzaMapper;

    public ResultSetToPizzaInfoMapper(ResultSetToPizzaMapper pizzaMapper) {
        this.pizzaMapper = pizzaMapper;
    }

    public IPizzaInfo fullMap(ResultSet resultSet) throws SQLException {
        return PizzaInfoBuilder.create()
                .setId(resultSet.getLong("pizza_info_id"))
                .setPizza(pizzaMapper.partialMap(resultSet))
                .setSize(resultSet.getInt("pizza_info_size"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }

    public IPizzaInfo partialMap(ResultSet resultSet) throws SQLException {
        return PizzaInfoBuilder.create()
                .setId(resultSet.getLong("pizza_info_id"))
                .setPizza(pizzaMapper.partialMap(resultSet))
                .setSize(resultSet.getInt("pizza_info_size"))
                .build();
    }
}