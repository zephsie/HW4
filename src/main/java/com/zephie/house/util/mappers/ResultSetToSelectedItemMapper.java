package com.zephie.house.util.mappers;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.builders.SelectedItemBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToSelectedItemMapper {
    public static ISelectedItem partialMap(ResultSet resultSet) throws SQLException {
        return SelectedItemBuilder.create()
                .setId(resultSet.getLong("selected_item_id"))
                .setMenuRow(ResultSetToMenuRowMapper.partialMap(resultSet))
                .setCount(resultSet.getInt("selected_item_count"))
                .build();
    }

    public static ISelectedItem fullMap(ResultSet resultSet) throws SQLException {
        return SelectedItemBuilder.create()
                .setId(resultSet.getLong("selected_item_id"))
                .setMenuRow(ResultSetToMenuRowMapper.partialMap(resultSet))
                .setCount(resultSet.getInt("selected_item_count"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }
}