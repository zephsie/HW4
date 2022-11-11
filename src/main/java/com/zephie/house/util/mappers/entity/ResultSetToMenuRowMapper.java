package com.zephie.house.util.mappers.entity;

import com.zephie.house.core.api.IMenuRow;
import com.zephie.house.core.builders.MenuRowBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToMenuRowMapper {
    private final ResultSetToPizzaInfoMapper resultSetToPizzaInfoMapper;
    private final ResultSetToMenuMapper resultSetToMenuMapper;


    public ResultSetToMenuRowMapper(ResultSetToPizzaInfoMapper resultSetToPizzaInfoMapper, ResultSetToMenuMapper resultSetToMenuMapper) {
        this.resultSetToPizzaInfoMapper = resultSetToPizzaInfoMapper;
        this.resultSetToMenuMapper = resultSetToMenuMapper;
    }

    public IMenuRow partialMap(ResultSet resultSet) throws SQLException {
        return MenuRowBuilder.create()
                .setId(resultSet.getLong("menu_row_id"))
                .setPizzaInfo(resultSetToPizzaInfoMapper.partialMap(resultSet))
                .setMenu(resultSetToMenuMapper.partialMap(resultSet))
                .setPrice(resultSet.getDouble("menu_row_price"))
                .build();
    }

    public IMenuRow fullMap(ResultSet resultSet) throws SQLException {
        return MenuRowBuilder.create()
                .setId(resultSet.getLong("menu_row_id"))
                .setPizzaInfo(resultSetToPizzaInfoMapper.partialMap(resultSet))
                .setMenu(resultSetToMenuMapper.partialMap(resultSet))
                .setPrice(resultSet.getDouble("menu_row_price"))
                .setCreateDate(resultSet.getTimestamp("dt_create").toLocalDateTime())
                .setUpdateDate(resultSet.getTimestamp("dt_update").toLocalDateTime())
                .build();
    }
}