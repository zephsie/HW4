package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.ISelectedItem;
import com.zephie.house.core.builders.SelectedItemBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToSelectedItemMapper {
    private final ResultSetToMenuRowMapper menuRowMapper;

    private final ResultSetToOrderMapper orderMapper;

    public ResultSetToSelectedItemMapper(ResultSetToMenuRowMapper menuMapper, ResultSetToOrderMapper orderMapper) {
        this.menuRowMapper = menuMapper;
        this.orderMapper = orderMapper;
    }

    public ISelectedItem partialMap(ResultSet resultSet) throws SQLException {
        return SelectedItemBuilder.create()
                .setId(resultSet.getLong("selected_item_id"))
                .build();
    }

    public ISelectedItem fullMap(ResultSet resultSet) throws SQLException {
        return SelectedItemBuilder.create()
                .setId(resultSet.getLong("selected_item_id"))
                .setMenuRow(menuRowMapper.partialMap(resultSet))
                .setCount(resultSet.getInt("selected_item_count"))
                .setOrder(orderMapper.partialMap(resultSet))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .build();
    }
}