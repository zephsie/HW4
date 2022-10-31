package com.zephie.house.util.mappers;

import com.zephie.house.core.api.IMenu;
import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.builders.MenuBuilder;
import com.zephie.house.core.builders.MenuRowBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToMenuMapper {
    public static IMenu fullMap(ResultSet resultSet) throws SQLException {
        return MenuBuilder.create()
                .setId(resultSet.getLong("menu_id"))
                .setName(resultSet.getString("menu_name"))
                .setActive(resultSet.getBoolean("menu_enable"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }

    public static IMenu partialMap(ResultSet resultSet) throws SQLException {
        return MenuBuilder.create()
                .setId(resultSet.getLong("menu_id"))
                .setName(resultSet.getString("menu_name"))
                .build();
    }

    public static IMenuRow fullMapRow(ResultSet resultSet) throws SQLException {
        return MenuRowBuilder.create()
                .setId(resultSet.getLong("menu_row_id"))
                .setPizzaInfo(ResultSetToPizzaInfoMapper.partialMap(resultSet))
                .setPrice(resultSet.getDouble("menu_row_price"))
                .build();
    }
}