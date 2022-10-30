package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.entity.Pizza;
import com.zephie.house.core.entity.PizzaInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ResultSetToPizzaInfoMapper {
    public static IPizzaInfo map(ResultSet resultSet) throws SQLException {
        return new PizzaInfo(
                resultSet.getLong("id"),
                new Pizza(
                        resultSet.getLong("pizza_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("pizza_dt_create").toLocalDateTime(),
                        resultSet.getTimestamp("pizza_dt_update").toLocalDateTime()
                ),
                resultSet.getInt("size"),
                resultSet.getObject("dt_create", LocalDateTime.class),
                resultSet.getObject("dt_update", LocalDateTime.class)
        );
    }
}
