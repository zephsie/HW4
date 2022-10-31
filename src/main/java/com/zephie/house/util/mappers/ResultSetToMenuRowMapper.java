package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.builders.MenuRowBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToMenuRowMapper {
    public static IMenuRow partialMap(ResultSet resultSet) throws SQLException {
        return MenuRowBuilder.create()
                .setId(resultSet.getLong("menu_row_id"))
                .setPizzaInfo(ResultSetToPizzaInfoMapper.partialMap(resultSet))
                .setMenu(ResultSetToMenuMapper.partialMap(resultSet))
                .setPrice(resultSet.getDouble("menu_row_price"))
                .build();
    }

    public static IMenuRow fullMap(ResultSet resultSet) throws SQLException {
        return MenuRowBuilder.create()
                .setId(resultSet.getLong("menu_row_id"))
                .setPizzaInfo(ResultSetToPizzaInfoMapper.partialMap(resultSet))
                .setMenu(ResultSetToMenuMapper.partialMap(resultSet))
                .setPrice(resultSet.getDouble("menu_row_price"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }
}