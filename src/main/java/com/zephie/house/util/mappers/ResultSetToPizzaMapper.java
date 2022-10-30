package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.entity.Pizza;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToPizzaMapper {
    public static IPizza map(ResultSet resultSet) throws SQLException {
        return new Pizza(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getTimestamp("dt_create").toLocalDateTime(),
            resultSet.getTimestamp("dt_update").toLocalDateTime()
        );
    }
}
